package com.neo.discovery.service.impl;

import com.neo.discovery.mapper.WaveMapper;
import com.neo.discovery.service.TongJiService;
import com.neo.discovery.vo.TongJiDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuyunpeng1 on 2018/6/19.
 */
@Service
public class TongJiServiceImpl implements TongJiService {

    @Resource
    private WaveMapper waveMapper;



    public List<TongJiDto> selectAvgByGroupTime() {
        List<TongJiDto>  list = waveMapper.selectAvgByGroupTime();

        return list;
    }
}
