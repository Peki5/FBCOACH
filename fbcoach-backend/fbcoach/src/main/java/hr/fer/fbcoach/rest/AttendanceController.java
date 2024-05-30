package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.Attendance;
import hr.fer.fbcoach.model.dto.AttendanceDTO;
import hr.fer.fbcoach.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public List<Attendance> listAll() {
        return attendanceService.getAllAttendances();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDTO> getAttendanceById(@PathVariable Long id) {
        return attendanceService.getAttendanceById(id)
                .map(attendance -> ResponseEntity.ok(convertToDto(attendance)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<AttendanceDTO> addAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        Attendance attendance = convertToEntity(attendanceDTO);
        Attendance createdAttendance = attendanceService.addAttendance(attendance);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdAttendance));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable Long id, @RequestBody AttendanceDTO attendanceDTO) {
        Attendance attendance = convertToEntity(attendanceDTO);
        return ResponseEntity.ok(convertToDto(attendanceService.updateAttendance(id, attendance)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/player/{playerId}")
    public List<AttendanceDTO> getHealthStatusesByPlayerId(@PathVariable Long playerId) {
        List<Attendance> attendances = attendanceService.getAttendanceByPlayerId(playerId);
        return attendances.stream()
                .sorted(Comparator.comparing(Attendance::getIdAttendance))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AttendanceDTO convertToDto(Attendance attendance) {
        return modelMapper.map(attendance, AttendanceDTO.class);
    }

    private Attendance convertToEntity(AttendanceDTO attendanceDTO) {
        return modelMapper.map(attendanceDTO, Attendance.class);
    }
}
