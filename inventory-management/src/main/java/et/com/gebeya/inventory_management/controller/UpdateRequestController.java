package et.com.gebeya.inventory_management.controller;

import et.com.gebeya.inventory_management.dto.request.VendorProductUpdateRequestDto;
import et.com.gebeya.inventory_management.dto.response.VendorProductUpdateResponseDto;
import et.com.gebeya.inventory_management.service.UpdateRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/vendor/request")
public class UpdateRequestController {

    private final UpdateRequestService updateRequestService;
    @PostMapping("/ask")
    public ResponseEntity<VendorProductUpdateResponseDto> createUpdateRequest(@RequestBody VendorProductUpdateRequestDto requestDto) {
        VendorProductUpdateResponseDto responseDto = updateRequestService.createUpdateRequest(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<VendorProductUpdateResponseDto> getUpdateRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(updateRequestService.getUpdateRequestById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<VendorProductUpdateResponseDto>> getAllUpdateRequests() {
        return ResponseEntity.ok(updateRequestService.getAllUpdateRequests());
    }

    @PutMapping("/{id}/approved")
    public ResponseEntity<VendorProductUpdateResponseDto> approvedUpdateRequest(@PathVariable Long id) throws IOException {
        VendorProductUpdateResponseDto responseDto = updateRequestService.approveVendorProductUpdate(id);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}/decline")
    public ResponseEntity<VendorProductUpdateResponseDto> declineUpdateRequest(@PathVariable Long id) throws IOException {
        VendorProductUpdateResponseDto responseDto = updateRequestService.declineUpdateRequest(id);
        return ResponseEntity.ok(responseDto);
    }

}
