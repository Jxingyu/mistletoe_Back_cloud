package com.xy.user.controller;

import com.xy.common.CommonResult;
import com.xy.model.Team;
import com.xy.model.TeamRecord;
import com.xy.user.service.TeamRecordService;
import com.xy.user.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    TeamService teamService;

    @Autowired
    TeamRecordService teamRecordService;

    /**
     * 团队模块——小队列表查询
     *
     * @return
     */
    @GetMapping("/findTeamAll")
//    @PreAuthorize("hasAuthority('team:select')")
    public CommonResult findTeamAll() {
        Vector<Team> vector = teamService.findTeamAll();
        return CommonResult.success(vector, "200");
    }

    /**
     * 团队模块——编辑窗口更新
     *
     * @param team
     * @return
     */
    @PostMapping("/edit/update")
//    @PreAuthorize("hasAuthority('team:edit')")
    public CommonResult editUpdate(@RequestBody Team team) {
        Integer integer = teamService.editUpdate(team);
        return CommonResult.success(integer, "200");
    }

    /**
     * 通过当前团队ID查询 相应数据
     *
     * @param numbers
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/findTeamById")
    public CommonResult findTeamByNumbers(@RequestParam("numbers") int numbers, HttpServletResponse response) throws IOException {
        Vector<Team> vector = teamService.findTeamById(numbers);
        return CommonResult.success(vector, "200");
    }


    /**
     * 删除团队
     *
     * @param numbers
     * @return
     */
    @PostMapping("/deleteTeam/{numbers}")
//    @PreAuthorize("hasAuthority('team:delete')")
    public CommonResult deleteTeam(@PathVariable("numbers") int numbers) {
        int result = teamService.deleteTeam(numbers);
        return CommonResult.success(result, "200");
    }

    /**
     * 调队记录
     * @return
     */
    @GetMapping("/findTeamRecord")
//    @PreAuthorize("hasAuthority('team:change:notes')")
    public CommonResult findTeamRecord() {
        Vector<TeamRecord> vector = teamRecordService.findTeamRecord();
        return CommonResult.success(vector, "200");
    }

    /*    *//**
     * 删除团队
     * @param numbers
     * @param pageNum
     * @return
     *//*
    @PostMapping({"/deleteTeam/{numbers}","/deleteTeam/{numbers}/{pageNum}"})
    public CommonResult deleteTeam(@PathVariable("numbers") int numbers,@PathVariable(value = "pageNum",required = false) int pageNum) {
        int result = teamService.deleteTeam(numbers);
        return CommonResult.success(result, "200");
    }*/
}
