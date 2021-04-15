package com.xy.word.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.model.Word;
import org.springframework.stereotype.Repository;

import java.util.Vector;

@Repository
public interface WordMapper extends BaseMapper<Word> {
    Vector<Word> findWordList(Word word);

    int findTotalCount(Word word);

    int updateNumber(Word word);

    int updateCollect(Word word);

    int wordAdd(Word word);

    Vector<Word> findCollectAll(Word word);
}
