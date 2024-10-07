package org.example.service.impl;

import org.example.data.BidData;
import org.example.data.UpdateData;
import org.example.json.Data;
import org.example.json.Json;
import org.example.json.JsonParser;
import org.example.json.PriceQty;
import org.example.populator.UpdatePopulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultMessagetServiceTest {

    private DefaultMessagetService messagetService;
    private JsonParser jsonParser;
    private UpdatePopulator updatePopulator;

    @BeforeEach
    void setUp() {
        // Mocking dependencies
        jsonParser = mock(JsonParser.class);
        updatePopulator = mock(UpdatePopulator.class);

        // Creating the service with mocked dependencies
        messagetService = new DefaultMessagetService();
        messagetService.setJsonParser(jsonParser);
        messagetService.setUpdatePopulator(updatePopulator);
    }

    @Test
    void testReadWithValidUpdateMessage() {
        // Prepare a valid message
        String message = "{\"type\":\"update\",\"data\":[{\"symbol\":\"AAPL\",\"timestamp\":1609459200}]}";

        // Mock the parsed Json object
        Json json = new Json();
        json.setType("update");

        Data data = new Data();
        data.setSymbol("AAPL");
        Date today = new Date();
        data.setTimestamp(today);

        List<Data> dataList = new ArrayList<>();
        dataList.add(data);
        json.setData(dataList);

        // Mock jsonParser behavior
        when(jsonParser.parse(message)).thenReturn(json);

        // Execute the read method
        messagetService.read(message);

        // Mocking the behavior of bidPopulator and askPopulator
        UpdateData capturedUpdateData = new UpdateData();
        capturedUpdateData.setTimestamp(today);
        capturedUpdateData.setSymbol("AAPL");

        doAnswer(invocation -> {
            UpdateData updateData = invocation.getArgument(1);
            updateData.setSymbol(((Data) invocation.getArgument(0)).getSymbol());
            return null;
        }).when(updatePopulator).populate(any(Data.class), any(UpdateData.class));

        assertEquals("AAPL", capturedUpdateData.getSymbol());
        assertEquals(today, capturedUpdateData.getTimestamp());
    }

    @Test
    void testReadWithNullJson() {
        // Prepare an invalid message that results in null Json
        String message = "invalid message";

        // Mock jsonParser to return null
        when(jsonParser.parse(message)).thenReturn(null);

        // Execute the read method
        messagetService.read(message);

        // Verify that updatePopulator was never called
        verify(updatePopulator, never()).populate(any(), any());

        // No output or additional behavior expected
    }

    @Test
    void testReadWithNonUpdateType() {
        // Prepare a message with a non-"update" type
        String message = "{\"type\":\"other\",\"data\":[]}";

        // Mock the parsed Json object
        Json json = new Json();
        json.setType("other");

        // Mock jsonParser behavior
        when(jsonParser.parse(message)).thenReturn(json);

        // Execute the read method
        messagetService.read(message);

        // Verify that updatePopulator was never called since type is not "update"
        verify(updatePopulator, never()).populate(any(), any());
    }

    @Test
    void testReadWithEmptyData() {
        // Prepare a valid message with empty data
        String message = "{\"type\":\"update\",\"data\":[]}";

        // Mock the parsed Json object
        Json json = new Json();
        json.setType("update");
        json.setData(new ArrayList<>());

        // Mock jsonParser behavior
        when(jsonParser.parse(message)).thenReturn(json);

        // Execute the read method
        messagetService.read(message);

        // Verify that updatePopulator was never called since data is empty
        verify(updatePopulator, never()).populate(any(), any());
    }
}
