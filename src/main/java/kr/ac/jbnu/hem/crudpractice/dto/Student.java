
package kr.ac.jbnu.hem.crudpractice.dto;

public class Student {

    private Integer id;
    private String name;
    private Integer grade;

    // 기본 생성자: JSON 데이터를 객체로 변환할 때 사용
    public Student() {
    }

    // 학생 정보를 직접 입력해서 객체를 생성할 때 사용
    public Student(Integer id, String name, Integer grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    // Getter: 데이터 조회
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getGrade() {
        return grade;
    }

    // Setter: 데이터 설정 및 수정
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
