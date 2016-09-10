package hackathon.neko.controller;

import hackathon.neko.model.Data;
import hackathon.neko.service.HtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;


/**
 * Created by rkuser on 2016/07/23.
 */
@Controller
public class NekoController {

    @Autowired
    HtmlService htmlService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "tmp";
    }

    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public List<Data> getData(){
        List<String> result = htmlService.extractFromHTML();
        List<Data> data= htmlService.convertToData(result);
        return Arrays.asList(data.get(0));
    }

}
