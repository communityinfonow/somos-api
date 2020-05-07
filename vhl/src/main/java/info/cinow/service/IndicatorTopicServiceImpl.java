package info.cinow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.model.IndicatorTopic;
import info.cinow.repository.IndicatorTopicDao;

@Service
public class IndicatorTopicServiceImpl implements IndicatorTopicService {

    @Autowired
    private IndicatorTopicDao dao;

    @Override
    public List<IndicatorTopic> getAllTopics() {
        // TODO Auto-generated method stub
        List<IndicatorTopic> indicatorTopics = new ArrayList<IndicatorTopic>();
        this.dao.findAll().forEach(topic -> {
            indicatorTopics.add(topic);
        });
        return indicatorTopics;

    }

}