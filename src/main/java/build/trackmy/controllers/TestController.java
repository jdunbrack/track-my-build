package build.trackmy.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import build.trackmy.ObjectMapperToMap;

@Controller
@RequestMapping("/")
public class TestController {
	
	private final ObjectMapperToMap obj;
	
	public TestController(ObjectMapperToMap obj) {
		this.obj = obj;
	}
	
	@RequestMapping(value="sendJson", method=RequestMethod.POST)
	public String receiveJson(@RequestParam("data") String data, Model model) {
		try {
			model.addAttribute("data", obj.readJsonToMap(data));
		} catch (JsonParseException e) {
			System.out.println("JsonParseException");
		} catch (JsonMappingException e) {
			System.out.println("JsonMappingException");
		} catch (IOException e) {
			System.out.println("IOException");
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("")
	public String showJson(Model model) {
		return "test.jsp";
	}

	
}
