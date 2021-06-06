package com.cpf.veadsool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cpf.veadsool.VeadSoolApplicationTest;
import com.cpf.veadsool.entity.GradeFiles;
import com.cpf.veadsool.mapper.GradeFilesMapper;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author caopengflying
 * @time 2020/3/3
 */

public class GradeFilesServiceImplTest extends VeadSoolApplicationTest {
    @Resource
    private GradeFilesServiceImpl gradeFilesService;
    @Resource
    private GradeFilesMapper gradeFilesMapper;

    @Test
    public void testCreate() {
        GradeFiles gradeFiles = new GradeFiles();
        gradeFiles.setAveScore(BigDecimal.TEN);
        gradeFiles.setBadCount(1);
        gradeFiles.setGradeId(1);
        gradeFiles.setGreatCount(20);
        gradeFiles.setMaxScore(BigDecimal.ZERO);
        gradeFiles.setMinScore(BigDecimal.ZERO);
        gradeFiles.setCreateUser(30);
        gradeFilesService.save(gradeFiles);
    }

    @Test
    public void testPage() {
        IPage<GradeFiles> page = new Page<>(1,2);
        IPage<GradeFiles> page1 = gradeFilesService.page(page);
        System.out.println(page1.getRecords());
        System.out.println(page1.getTotal());
    }

}