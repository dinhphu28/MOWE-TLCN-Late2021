package com.ndp.audiosn.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.ndp.audiosn.Entities.Article;
import com.ndp.audiosn.Entities.ArticleReport;
import com.ndp.audiosn.Models.ArticleReport.ArticleReportCreateModel;
import com.ndp.audiosn.Models.ArticleReport.ArticleReportItemModel;
import com.ndp.audiosn.Models.ArticleReport.ArticleReportUpdateModel;
import com.ndp.audiosn.Services.ArticleReportService;
import com.ndp.audiosn.Services.ArticleService;
import com.ndp.audiosn.Utils.DateTime.MyDateTimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/reports")
public class ArticleReportREST {
    @Autowired
    private ArticleReportService articleReportService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MyDateTimeUtils myDateTimeUtils;

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveAllArticleReport(@RequestParam(value = "solved", required = true) Boolean solved) {
        ResponseEntity<Object> entity;

        // List<ArticleReport> articleReports = articleReportService.retrieveAll();
        List<ArticleReport> articleReports = articleReportService.retrieveBySolved(solved);
        
        List<ArticleReportItemModel> articleReportItemModels = new ArrayList<ArticleReportItemModel>();

        for(ArticleReport item : articleReports) {
            Article tmpArticle = articleService.retrieveOne(item.getArticleId());

            ArticleReportItemModel tmpARIModel = new ArticleReportItemModel(item.getId(), item.getArticleId(), item.getAuthor(),
                item.getDate(), item.getTime(), item.getContent(),
                tmpArticle.getTitle(), tmpArticle.getUrl(), item.getSolved());

            articleReportItemModels.add(tmpARIModel);
        }

        entity = new ResponseEntity<>(articleReportItemModels, HttpStatus.OK);

        return entity;
    }

    @GetMapping(
        value = "/{articleId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveAllByArticleId(@PathVariable("articleId") Integer articleId) {
        ResponseEntity<Object> entity;

        List<ArticleReport> articleReports = articleReportService.retrieveAllByArticleId(articleId);

        entity = new ResponseEntity<>(articleReports, HttpStatus.OK);

        return entity;
    }

    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> createNewReport(@RequestBody ArticleReportCreateModel articleReport) {
        ResponseEntity<Object> entity;

        if(articleReport.getArticleId() == null || articleReport.getAuthor() == null || articleReport.getContent() == null) {

            entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
        } else {
            ArticleReport tmpAR = articleReport.toArticleReport(myDateTimeUtils.getCurrentDate(), myDateTimeUtils.getCurrentTime());

            ArticleReport tmpToSave = articleReportService.createOne(tmpAR);

            if(tmpToSave == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
            } else {
                entity = new ResponseEntity<>(tmpToSave, HttpStatus.CREATED);
            }
        }

        return entity;
    }

    @PutMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> markSolvedOrUnSolve(@PathVariable("id") Integer id, @RequestBody ArticleReportUpdateModel articleReportUpdateModel) {
        ResponseEntity<Object> entity;

        ArticleReport tmpToSave = articleReportService.retrieveOne(id);

        if(tmpToSave == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
        } else {
            tmpToSave.setSolved(articleReportUpdateModel.getSolved());

            ArticleReport tmpSaved = articleReportService.updateOne(tmpToSave);

            if(tmpSaved == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                entity = new ResponseEntity<>(tmpSaved, HttpStatus.OK);
            }
        }
        
        return entity;
    }
}
