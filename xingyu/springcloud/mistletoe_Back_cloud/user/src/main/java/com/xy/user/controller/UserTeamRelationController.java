package com.xy.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xy.common.CommonResult;
import com.xy.model.Team;
import com.xy.model.User;
import com.xy.model.UserCaptainRelation;
import com.xy.model.UserTeamRelation;
import com.xy.user.service.UserCaptainService;
import com.xy.user.service.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

@RestController
@RequestMapping("/utr")
public class UserTeamRelationController {
    @Autowired
    UserTeamService userTeamService;

    @Autowired
    UserCaptainService userCaptainService;
    /**
     * 团队模块——小队列表—— 队员分配当前小队" 所有 "队员 查询(编辑窗口)
     * 当前在团队里的队员查询 selectUserByUserIdInUtr
     *
     * @return
     */
    @GetMapping("/selectUser")
    public void selectUserByUserIdInUtr(HttpServletResponse response) throws IOException {
        List<User> vector = userTeamService.selectUserByTeamNumber();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", vector);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 查询当前团队 队员
     *
     * @param id       为SQL__numbers条件
     * @param response
     * @throws IOException
     */
    @GetMapping("/selectUserByTeamId")
    public void selectUserByTeamId(@RequestParam int id, HttpServletResponse response) throws IOException {
        Vector<User> vector = userTeamService.selectUserByTeamId(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", vector);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 查询队员关联表数据 更新utr表用
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/selectUtr")
    public void selectUtr(@RequestParam int id, HttpServletResponse response) throws IOException {
        Vector<UserTeamRelation> vector = userTeamService.selectUtr(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", vector);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * utr表 左边数据 插入到右边(新增队员)
     *
     * @param utr
     * @param
     * @throws IOException
     */
    @PostMapping("/insertUserTeam")
    public CommonResult insertUserTeam(@RequestBody List<UserTeamRelation> utr) {
        CommonResult integer = userTeamService.insertUserTeam(utr);
        return CommonResult.success(integer, "200");
    }

    /**
     * utr表 右边数据 删除此队队员 (删除队员)
     *
     * @param utr
     * @return
     */
    @PostMapping("/deleteUtrIdInUtr")
    public CommonResult deleteUtrIdInUtr(@RequestBody List<UserTeamRelation> utr) {
        Integer integer = userTeamService.deleteUtrIdInUtr(utr);
        return CommonResult.success(integer, "200");
    }

    /**
     * 查询所有队员作为 队长候选人 ——队长编辑
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/select/captain")
    public void selectCaptainInUser(HttpServletResponse response) throws IOException {
        List<User> vector = userTeamService.selectCaptainInUser();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", vector);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 查询当前队长
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/select/NowCaptain")
    public void selectCaptainIsUsername(@RequestParam int id, HttpServletResponse response) throws IOException {
        List<Team> vector = userTeamService.selectCaptainIsUsername(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", vector);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 查询队员关联表数据 更新ucr表用
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/selectUcr")
    public void selectUcr(@RequestParam int id, HttpServletResponse response) throws IOException {
        List<UserCaptainRelation> vector = userTeamService.selectUcr(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", vector);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }


    @PostMapping("/update/NowCaptain")
//    @PreAuthorize("hasAuthority('team:captain')")
    public CommonResult updateNowCaptain(@RequestBody List<UserCaptainRelation> ucr) {
        int result = userCaptainService.updateNowCaptain(ucr);
        return CommonResult.success(result, "200");
    }

}
