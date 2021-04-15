package com.xy.word.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xy.common.CommonResult;
import com.xy.model.Word;
import com.xy.word.service.IWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;

@RestController
@RequestMapping("/word")
public class WordController {
    @Autowired
    IWordService wordService;

    @GetMapping("/findAll")
    public void findWordList(HttpServletResponse response, Word word) throws IOException {
        Vector<Word> words = wordService.findWordList(word);
        int totalCount = wordService.findTotalCount(word);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", words);
        jsonObject.put("totalCount", totalCount);
        response.setContentType("application/json;charset=utf-8");
//        String s = JSON.toJSONString(jsonObject);
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }
    @GetMapping("/findCollectAll")
    public void findCollectAll(HttpServletResponse response, Word word) throws IOException {
        Vector<Word> words = wordService.findCollectAll(word);
        int totalCount = wordService.findTotalCount(word);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", words);
        jsonObject.put("totalCount", totalCount);
        response.setContentType("application/json;charset=utf-8");
//        String s = JSON.toJSONString(jsonObject);
        response.getWriter().write(JSON.toJSONString(jsonObject));
    }

    /**
     * 单词点赞
     *
     * @param word
     * @return
     */
    @PostMapping("/updateNumber")
    public CommonResult updateNumber(Word word) {
        int number = wordService.updateNumber(word);
        return CommonResult.success(number);
    }

    /**
     * 单词收藏
     *
     * @param word
     * @return
     */
    @PostMapping("/updateCollect")
    public CommonResult updateCollect(Word word) {
        int number = wordService.updateCollect(word);
        return CommonResult.success(number);
    }

    @PostMapping("/wordAdd")
    public CommonResult wordAdd(@RequestBody Word word) {
        int number = wordService.wordAdd(word);
            return CommonResult.success(number);
    }
}
