package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    //1. 정적 컨텐츠 방식
    //web application 에서 /hello라고 들어오면 해당 메서드(hello 메서드)를 호출한다
    //GetMapping 에서 Get은 web에서 GET과 POST를 말한다. GET방식
    @GetMapping("hello")//hello url에 매칭됨
    public String hello(Model model) {//스프링이 model을 만들어서 넣어줌
        model.addAttribute("data", "spring!!");
        return "hello";//템플릿의 hello.html로 가서 렌더링해라
    }

    //2. MVC 방식(템플릿 엔진으로 렌더링하여 페이지 반환)
    //@RequestParam(name = "name", required = true)에서 required = true 가 default이므로 생략 가능
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        //"name"이 key,
        model.addAttribute("name", name);
        return "hello-template";
    }
    //viewResolver : 뷰를 찾아주고 템플릿엔진 연결

    //@ResponseBody : html의 body태그를 말한는 것이 아님
    //                http의 바디부에 내가 응답을 직접 넣어주겠다는 것
    //                페이지 소스를 보면 html 코드가 있는게 아니라 return 값만 그대로 나온다.
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; //"hello spring" 요청한 클라이언트에 그대로 감(템플릿 엔진 과의 차이점은 뷰가 없고 그대로 내려감)
    }

    //2. API
    //데이터를 내놓으라고 할 때 API 많이 사용

    //@ResponseBody
    //객체를 문자로 바꿔서 반환해야 한다.-> Json으로 보낸다
    //@ResponseBody가 안 붙어있으면 viewResolver에 던지는데, Responsebody가 붙어있으면
    //http 응답에 그대로 데이터를 넘겨야 되겟다고 동작을 한다.
    //그런데 넘기는 것이 객체이기 때문에
    //객체가 반환되면 스프링 입장에서는 한 번 생각 -> default가 JSON방식으로 데이터를 만들어서
    //http 응답에 반환한다.
    //기존의 viewResolver가 동작하는 것이 아니라 HttpMessageConverter가 동작한다
    //문자면 StringConverter가 동작, 객체면 JsonConvertor가 동작
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;//문자가 아닌 객체 반환(spring 입장에서는 한 번 생각-> 객체가 오면 기본적을 JSON방식으로 만들어서 반환한다)
        //웹페이지 출력(JSON방식으로 출력) : {"name":"spring!!!"}
        //JSON : key, value로 이루어진 구조 -> name(key), spring!!!(value)

    }

    static class Hello {
        private String name;
        //getter, setter 방식 : property 접근방식
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
