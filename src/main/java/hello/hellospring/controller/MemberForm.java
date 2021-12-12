package hello.hellospring.controller;

public class MemberForm {
    //createMemberForm.html의 name과 MemberForm class의 iv인 name이 matching 되면서 데이터 들어온다.
    private String name;

    public String getName() {
        return name;
    }
    //Spring Container 가 setter 를 통해 name 데이터를 넣어준다
    public void setName(String name) {
        this.name = name;
    }
}
