package com.example.incidentmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateIncident() throws Exception {
        String json = """
                    {
                        "title": "Incident#1",
                        "description": "This is a open incident",
                        "status": "1"
                    }
                """;

        mockMvc.perform(post("/api/incidents/createIncident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0))
                .andExpect(jsonPath("$.data.title").value("Incident#1"));
    }

    @Test
    void testQueryAllIncidents() throws Exception {
        mockMvc.perform(post("/api/incidents/queryAllIncidents")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testModifyIncident() throws Exception {
        String createJson = """
                    {
                "title": "Incident#2",
                "description": "This is a open incident",
                "status": "1"
                    }
                """;

        // Create an incident first
        mockMvc.perform(post("/api/incidents/createIncident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isOk());

        String modifyJson = """
                                {
                                    "id": 1,
                                    "title": "Incident#1",
                                    "description": "This is a incident in progress",
                                    "status": "2"
                                }
                """;

        // Modify the incident
        mockMvc.perform(post("/api/incidents/modifyIncident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modifyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0))
                .andExpect(jsonPath("$.data.status").value("2"));
    }

    @Test
    void testDeleteIncident() throws Exception {
        String createJson = """
                    {
                "title": "Incident#3",
                "description": "This is a closed incident",
                "status": "0"
                    }
                """;
        // Create an incident first
        mockMvc.perform(post("/api/incidents/createIncident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isOk());

        String deleteJson = """
                    {
                        "id": 3
                    }
                """;
        // Delete the incident
        mockMvc.perform(post("/api/incidents/deleteIncident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deleteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode").value(0))
                .andExpect(jsonPath("$.data").value("Incident deleted successfully"));
    }

    @Test
    void testModifyNonExistentIncident() throws Exception {
        String modifyJson = """
                    {
                        "id": 999,
                        "title": "Non-Existent Incident",
                        "description": "This incident does not exist",
                        "status": "0"
                    }
                """;

        mockMvc.perform(post("/api/incidents/modifyIncident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modifyJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(1))
                .andExpect(jsonPath("$.message").value("IncidentEntity not found"));
    }
}
