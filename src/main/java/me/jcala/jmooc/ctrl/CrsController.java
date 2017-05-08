package me.jcala.jmooc.ctrl;

import me.jcala.jmooc.entity.Course;
import me.jcala.jmooc.exception.NoPageException;
import me.jcala.jmooc.service.inter.CrsSer;
import me.jcala.jmooc.utils.CommonUtils;
import me.jcala.jmooc.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 *  课程管理控制器
 */
@Controller
public class CrsController {

    private CrsSer crsSer;

    @Autowired
    public CrsController(CrsSer crsSer) {
        this.crsSer = crsSer;
    }

    @GetMapping("/user/tea/crs_mgr")
    public String courseManagerPage(@RequestParam("do") String operate){
        if (operate==null || CommonUtils.isEmpty(operate)){
            throw new NoPageException();
        }
        if ("add".equals(operate.trim())){
            return "crs_mgr_add";
        }else if ("del".equals(operate.trim())){
            return "crs_mgr_del";
        }else if ("mod".equals(operate.trim())){
            return "crs_mgr_mod";
        }else {
            throw new NoPageException();
        }

    }

    @PostMapping("/user/tea/crs_mgr/add.do")
    public String addCourse(@ModelAttribute("course") @Valid Course course,
                            BindingResult result,
                            HttpServletRequest request){

        int userId= RequestUtils.getUserIdFromReq(request);

        if (result.hasErrors()) {
            return "crs_mgr_add";
        }
        course.setUserId(userId);
        int crsId=crsSer.addCourse(course);
        return "redirect:/user/tea/chp_mgr?crs_id="+crsId;
    }

    @GetMapping("/user/tea/chp_mgr")
    public String chpMgr(@RequestParam("crs_id") int crsId,Model model){

        return "chp_mgr";
    }

}
