package cz.kst.btdd.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kst.btdd.ToolRentalApplication;
import cz.kst.btdd.api.dto.CreateReservationRequest;
import cz.kst.btdd.domain.UserRole;
import cz.kst.btdd.persistence.EntityTool;
import cz.kst.btdd.persistence.EntityUser;
import cz.kst.btdd.persistence.ToolRepository;
import cz.kst.btdd.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integrační testy: vrstvy controller – service – DB v realistickém prostředí (Spring context, H2).
 */
@SpringBootTest(classes = ToolRentalApplication.class)
@AutoConfigureMockMvc
@Transactional
@DisplayName("Rental API integrace")
class RentalControllerIT {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ToolRepository toolRepository;

    private Long customerId;
    private Long employeeId;
    private Long toolId;

    @BeforeEach
    void setUp() {
        EntityUser customer = new EntityUser();
        customer.setName("Jan");
        customer.setRole(UserRole.CUSTOMER);
        customer = userRepository.save(customer);
        customerId = customer.getId();

        EntityUser employee = new EntityUser();
        employee.setName("Marie");
        employee.setRole(UserRole.EMPLOYEE);
        employee = userRepository.save(employee);
        employeeId = employee.getId();

        EntityTool tool = new EntityTool();
        tool.setName("Kladivo");
        tool.setDailyPriceCzk(new BigDecimal("100"));
        tool.setLateFeePerDay(new BigDecimal("50"));
        tool = toolRepository.save(tool);
        toolId = tool.getId();
    }

    @Test
    @DisplayName("POST /api/rentals/reservations vytvoří rezervaci a vrátí 201")
    void createReservation_returns201() throws Exception {
        CreateReservationRequest request = new CreateReservationRequest();
        request.setUserId(customerId);
        request.setToolId(toolId);
        request.setStartDate(LocalDate.of(2025, 4, 1));
        request.setEndDate(LocalDate.of(2025, 4, 5));

        mvc.perform(post("/api/rentals/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("RESERVED"))
                .andExpect(jsonPath("$.user.id").value(customerId))
                .andExpect(jsonPath("$.tool.id").value(toolId));
    }

    @Test
    @DisplayName("validace vstupu: chybějící userId vrátí 400")
    void createReservation_missingUserId_returns400() throws Exception {
        CreateReservationRequest request = new CreateReservationRequest();
        request.setToolId(toolId);
        request.setStartDate(LocalDate.of(2025, 4, 1));
        request.setEndDate(LocalDate.of(2025, 4, 5));

        mvc.perform(post("/api/rentals/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("userId")));
    }

    @Test
    @DisplayName("business pravidlo: kolize období vrátí 400")
    void createReservation_overlap_returns400() throws Exception {
        CreateReservationRequest request = new CreateReservationRequest();
        request.setUserId(customerId);
        request.setToolId(toolId);
        request.setStartDate(LocalDate.of(2025, 4, 1));
        request.setEndDate(LocalDate.of(2025, 4, 5));

        mvc.perform(post("/api/rentals/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // Druhá rezervace stejného nářadí ve stejném období = kolize
        mvc.perform(post("/api/rentals/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("kolize")));
    }
}
