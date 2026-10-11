
package kr.ac.jbnu.hem.crudpractice;

import kr.ac.jbnu.hem.crudpractice.dto.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    // 학생 정보를 저장하는 HashMap
    private final Map<Integer, Student> students = new HashMap<>();

    // 처음 실행할 때 학생 2명 등록
    public StudentController() {
        students.put(1001, new Student(1001, "철수", 2));
        students.put(1002, new Student(1002, "영희", 3));
    }

    // 공통 응답 생성
    private ResponseEntity<Map<String, Object>> response(
            int code, String status, Object data) {

        return ResponseEntity.status(code).body(
                Map.of(
                        "status", status,
                        "data", data
                )
        );
    }

    // 학생 정보 유효성 검사
    private boolean isInvalid(Student student) {
        return student == null
                || student.getId() == null
                || student.getName() == null
                || student.getName().isBlank()
                || student.getGrade() == null
                || student.getGrade() < 1;
    }

    // GET 1: 전체 학생 조회
    @GetMapping("/students")
    public ResponseEntity<Map<String, Object>> getStudents() {
        return response(200, "success", students.values());
    }

    // GET 2: 특정 학생 조회
    @GetMapping("/students/{id}")
    public ResponseEntity<Map<String, Object>> getStudent(
            @PathVariable Integer id) {

        Student student = students.get(id);

        if (student == null) {
            return response(404, "error", "해당 학생이 없습니다.");
        }

        return response(200, "success", student);
    }

    // POST 1: 학생 한 명 등록
    @PostMapping("/students")
    public ResponseEntity<Map<String, Object>> createStudent(
            @RequestBody Student student) {

        if (isInvalid(student)) {
            return response(400, "error", "학생 정보가 올바르지 않습니다.");
        }

        students.put(student.getId(), student);

        return response(201, "success", student);
    }

    // POST 2: 여러 학생 일괄 등록
    @PostMapping("/students/batch")
    public ResponseEntity<Map<String, Object>> createStudents(
            @RequestBody List<Student> newStudents) {

        if (newStudents == null || newStudents.isEmpty()) {
            return response(400, "error", "등록할 학생이 없습니다.");
        }

        for (Student student : newStudents) {
            if (isInvalid(student)) {
                return response(400, "error", "잘못된 학생 정보가 포함되어 있습니다.");
            }
        }

        List<Student> savedStudents = new ArrayList<>();

        for (Student student : newStudents) {
            students.put(student.getId(), student);
            savedStudents.add(student);
        }

        return response(201, "success", savedStudents);
    }

    // PUT 1: 학생 이름과 학년 수정
    @PutMapping("/students/{id}")
    public ResponseEntity<Map<String, Object>> updateStudent(
            @PathVariable Integer id,
            @RequestBody Student updatedStudent) {

        Student student = students.get(id);

        if (student == null) {
            return response(404, "error", "해당 학생이 없습니다.");
        }

        if (updatedStudent == null
                || updatedStudent.getName() == null
                || updatedStudent.getName().isBlank()
                || updatedStudent.getGrade() == null
                || updatedStudent.getGrade() < 1) {
            return response(400, "error", "수정할 정보가 올바르지 않습니다.");
        }

        student.setName(updatedStudent.getName());
        student.setGrade(updatedStudent.getGrade());

        return response(200, "success", student);
    }

    // PUT 2: 학생 학년만 수정
    @PutMapping("/students/{id}/grade")
    public ResponseEntity<Map<String, Object>> updateStudentGrade(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> request) {

        Student student = students.get(id);

        if (student == null) {
            return response(404, "error", "해당 학생이 없습니다.");
        }

        Integer newGrade = request.get("grade");

        if (newGrade == null || newGrade < 1) {
            return response(400, "error", "학년 정보가 올바르지 않습니다.");
        }

        student.setGrade(newGrade);

        return response(200, "success", student);
    }

    // DELETE 1: 특정 학생 삭제
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(
            @PathVariable Integer id) {

        Student removedStudent = students.remove(id);

        if (removedStudent == null) {
            return response(404, "error", "해당 학생이 없습니다.");
        }

        return response(200, "success", removedStudent);
    }

    // DELETE 2: 전체 학생 삭제
    @DeleteMapping("/students")
    public ResponseEntity<Map<String, Object>> deleteAllStudents() {

        int deletedCount = students.size();
        students.clear();

        return response(
                200,
                "success",
                Map.of("deletedCount", deletedCount)
        );
    }


    // 500 테스트: 서버 내부 오류 상황 재현
    @GetMapping("/students/test/error")
    public ResponseEntity<Map<String, Object>> testServerError() {

        try {
            throw new IllegalStateException("테스트용 서버 오류");
        } catch (IllegalStateException e) {
            return response(
                    500,
                    "error",
                    "서버 내부 오류가 발생했습니다."
            );
        }
    }

    // 503 테스트: 서버 점검 상태 재현
    @GetMapping("/students/test/maintenance")
    public ResponseEntity<Map<String, Object>> testMaintenance() {

        return response(
                503,
                "error",
                "현재 서버 점검 중입니다."
        );
    }
}
