package tests;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.clients.APIClient;
import core.models.Booking;
import io.restassured.response.Response;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class testDeleteBookingById {

    private APIClient apiClient;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        apiClient = new APIClient();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testDeleteBookingById() throws Exception {

        Response allBookingsResponse = apiClient.getBooking();
        assertThat(allBookingsResponse.getStatusCode()).isEqualTo(200);

        String allBookingsBody = allBookingsResponse.getBody().asString();
        List<Booking> allBookings = objectMapper.readValue(allBookingsBody, new TypeReference<List<Booking>>() {
        });
        assertThat(allBookings).isNotEmpty();

        int deletedBookingId = allBookings.get(0).getBookingid();
        System.out.println("Удалённый айдишник - " + deletedBookingId);

        apiClient.createToken("admin", "password123");
        apiClient.deleteBooking(deletedBookingId);
        System.out.println(apiClient.getBooking().getBody().asString());
    }
}
