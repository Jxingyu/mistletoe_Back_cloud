package com.xy.daily.controller;

import com.xy.common.CommonResult;
import com.xy.daily.service.IDailyService;
import com.xy.model.Daily;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Vector;

//swagger 控制层的类上
@Api(tags = "日报管理")
@RestController
@RequestMapping("/daily")
public class DailyController {
    @Autowired
    IDailyService iDailyService;

    /**
     * 查询用户id By 用户名
     *
     * @param username
     * @return
     */
    @GetMapping(path = {"/selectDailyId"})
    public CommonResult selectDailyId(@RequestParam("username") String username) {
        Integer id = iDailyService.selectDailyId(username);
        return CommonResult.success(id, "200");
    }

    /**
     * 查询该用户所有日报
     *
     * @param userId
     * @return
     */
    @GetMapping("/selectDailyAll/{username}")
//    @PreAuthorize("hasAuthority('self:daily')")//我的日报
    public CommonResult selectDailyAll(@PathVariable("username") Integer userId) {
        Vector<Daily> vector = iDailyService.selectDailyAll(userId);
        return CommonResult.success(vector, "200");
    }


    /**
     * 查询出当前写日报用户的
     * 教练 助理
     *
     * @param userId
     * @return
     */
    @GetMapping("/selectAOrCById/{userId}")
    public CommonResult selectDailyEditMseByUserId(@PathVariable("userId") Integer userId) {
        Vector<Daily> vector = iDailyService.selectDailyEditMes(userId);
        return CommonResult.success(vector, "200");
    }

    /**
     * 提交日报到数据库
     *
     * @param daily
     * @return
     */
    @PostMapping(value = {"/insertDaily"})
//    @PreAuthorize("hasAuthority('daily:update')")//提交日报
    public CommonResult insertDaily(@RequestBody Daily daily) {
        int i = iDailyService.insertDaily(daily);
        return CommonResult.success(i, "200");
    }

    /**
     * 保存日报草稿
     *
     * @param daily
     * @return
     */
    @PostMapping(value = {"/saveDailyInRedis"})
//    @PreAuthorize("hasAuthority('daily:save')")//保存日报为草稿
    public CommonResult saveDailyInRedis(@RequestBody Daily daily) {
        int i = iDailyService.saveDailyInRedis(daily);
        return CommonResult.success(i, "200");
    }

    /**
     * 通过id查询草稿状态的 日志 赋值用
     *
     * @param id
     * @return
     */
    @GetMapping("/selectDailyDraftById/{id}")
    public CommonResult selectDailyById(@PathVariable("id") int id) {
        Vector<Daily> vector = iDailyService.selectDailyById(id);
        return CommonResult.success(vector, "200");
    }

    /**
     * 更新草稿状态的日志 为已经提交
     *
     * @param daily
     * @return
     */
    @PostMapping(value = {"/updateDraftDaily"})
    public CommonResult updateDraftDaily(@RequestBody Daily daily) {
        int i = iDailyService.updateDraftDaily(daily);
        return CommonResult.success(i, "200");
    }

    //swagger 方法上
    @ApiOperation("日报状态更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id标识", required = true, paramType = "path"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, paramType = "path")
    })
    @PostMapping("/updateDailyStatus/{id}/{status}")
    public CommonResult updateDailyStatus(@PathVariable("id") int id, @PathVariable("status") String status) {
        int i = iDailyService.updateDailyStatus(id, status);
        return CommonResult.success(i, "200");
    }

    /**
     * 审查日报
     *
     * @return
     */
    @GetMapping(value = {"/ReviewDailyAll"})
//    @PreAuthorize("hasAuthority('review:daily')")//审查日报
    public CommonResult ReviewDailyAll() {
        Vector<Daily> vector = iDailyService.ReviewDailyAll();
        return CommonResult.success(vector, "200");
    }

    /**
     * 导出Word文档到本地
     *
     * @param daily
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping(value = {"/ExportWord"})
//    @PreAuthorize("hasAuthority('daily:export:word')")//导出日报
    public CommonResult ExportWord(@RequestBody Daily daily, HttpServletResponse response) throws Exception {
        //导出
        String pathName = iDailyService.ExportWord(daily, response);

        //下载
//        iDailyService.downLoad(pathName, daily.getId(), response);
        return CommonResult.success(pathName, "200");
    }

}
