package hackathon.neko.controller;

import hackathon.neko.model.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by rkuser on 2016/07/23.
 */
@Controller
public class NekoController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String get(){
        return "tmp";
    }

    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public Data neko(){
        return new Data("title", "code", "result");
    }

}
