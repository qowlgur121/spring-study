package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    //아래는 템플릿 엔진 방식. 정적인 HTML 코드에 동적인 데이터를 삽입해서 웹페이지가 동적이 되도록 돕는다.
    //GetMapping은 HTTP GET 요청을 처리하는 메서드. HTTP GET 요청? 이것은 웹 브라우저에서 주소를 입력하거나 링크를 클릭할 때 사용되는 방식
    @GetMapping("hello") // /hello URL 요청을 처리하는 핸들러. 핸들러는 요청을 처리하는 역할을 함
    public String hello(Model model) { //Model은 뷰에 필요한 데이터를 전달하는 역할을 하는 클래스이다. 객체를 통해 뷰에 전달할 데이터를 추가한다.
        //data라는 이름으로 "hello!!" 값을 뷰에 전달한다.
        model.addAttribute("data", "hello!!");
        return "hello"; //resources/templates/hello.html 템플릿을 렌더링하도록 지시한다.
        //템플릿은 미리 만들어진 틀이다.
    }

    //아래는 API 방식이다. 데이터를 주고받는 것을 API인데 이때 주고 받는 형식이 주로 JSON형식이다. 만약 객체가 보내지면 JSON으로 변환해야 한다.
    @GetMapping("hello-mvc") // /hello-mvc URL 요청을 처리하는 핸들러
    //RequestParam("name") String name은 요청 URL
    public String helloMvc(@RequestParam(value = "name") String name, Model model) { //
        model.addAttribute("name", name); //
        return "hello-template"; //resources/templates/hello-template.html을 렌더링하도록 지시한다.
        // 이렇게 hello-template처럼 뷰 이름만 반환된 경우, 기본적으로 resources/templates에서만 템플릿 파일을 찾는다.
    }
    //이거는 일단 웹 브라우저에서 /hello-mvc 요청을 했을 때 /hello-mvc?name=hi!! 와 같이
    // ?뒤에 있는 쿼리 파라미터 hi!!를 name에다가 넣고 이 컨트롤러가 처리할 때 name을 hello-template 뷰에 있는 name으로 보내주는거다.


    /*
    <아래의 함수 과정>
    1. 먼저 사용자가 /hello-string?name=spring! URL로 요청을 보낸다.
    2. 스프링은 @GetMapping("hello-string") 이 어노테이션을 기반으로 이 메서드를 찾아 호출한다.
    3. @RequestParam("name") 어노테이션은 요청 URL에 있는 name 쿼리 파라미터의 값 "spring!"을 추출하여 name변수에 전달한다.
    3. 이 메서드는 결국 "hello spring!"을 반환한다.
     */
    @GetMapping("hello-string")
    @ResponseBody
    //@ResponseBody 어노테이션을 쓰면 리턴값은 응답 본문 데이터 그 자체이다.
    //위 helloMvc에서는 @ResponseBody 어노테이션을 쓰지 않았기에 리턴된 문자열을 기반으로 뷰를 찾는다.
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; //"hello spring"
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
        //@RequestBody 어노테이션 덕분에 hello객체는 Json으로 변환된다.
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    //@ResponseBody 사용x : 리턴값을 기반으로 뷰를 찾는다.
    //@ResponseBody 사용o : 리턴값을 뷰가 아닌 JSON으로 전송한다.
}


