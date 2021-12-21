package com.ndp.audiosn.Models.ArticleReport;

import java.util.List;

import com.ndp.audiosn.Entities.ArticleReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReportReturnModel {
    private Integer numberOfReports;

    private List<ArticleReport> articleReports;
}
