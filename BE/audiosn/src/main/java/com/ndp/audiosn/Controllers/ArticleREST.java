package com.ndp.audiosn.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.ndp.audiosn.Entities.Article;
import com.ndp.audiosn.Entities.Comment;
import com.ndp.audiosn.Entities.UserVoteState;
import com.ndp.audiosn.Models.Article.ArticleCreateModel;
import com.ndp.audiosn.Models.Article.ArticleItemReturnModel;
import com.ndp.audiosn.Models.Article.ArticleUpdateModel;
import com.ndp.audiosn.Models.Article.PageOfArticleModel;
import com.ndp.audiosn.Models.Comment.CommentCreateModel;
import com.ndp.audiosn.Models.Comment.CommentUpdateModel;
import com.ndp.audiosn.Models.UserVoteState.UVSReturnModel;
import com.ndp.audiosn.Models.UserVoteState.UVSUpdateModel;
import com.ndp.audiosn.Services.ArticleReportService;
import com.ndp.audiosn.Services.ArticleService;
import com.ndp.audiosn.Services.CommentService;
import com.ndp.audiosn.Services.UserVoteStateService;
import com.ndp.audiosn.Utils.Auth.JWT.jwtSecurity;
import com.ndp.audiosn.Utils.Auth.JWT.myJWT;
import com.ndp.audiosn.Utils.Auth.TokenProcessing.AuthHeaderProcessing;
import com.ndp.audiosn.Utils.DateTime.MyDateTimeUtils;
import com.ndp.audiosn.Utils.UriParser.MyUriParserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/articles")
public class ArticleREST {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MyDateTimeUtils myDateTimeUtils;

    @Autowired
    private MyUriParserUtils myUriParserUtils;

    @Autowired
    private AuthHeaderProcessing authHeaderProcessing;

    @Autowired
    private UserVoteStateService userVoteStateService;

    @Autowired
    private ArticleReportService articleReportService;

    @GetMapping (
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveAllArticles(@RequestParam(value = "page", required = true) Integer pageNum, @RequestParam(value = "category", required = false) String categoryName) {
        
        PageOfArticleModel pageOfArticleModel = new PageOfArticleModel();

        if (categoryName == null) {
            List<Article> articles = articleService.retrieveOneCommonPage(pageNum);

            Integer noPage = (int)Math.ceil(Double.valueOf(articleService.retrieveNumOfPages(categoryName).intValue()) / 10); // ceiling number of pages and convert to Integer

            List<ArticleItemReturnModel> articleItemReturnModels = new ArrayList<ArticleItemReturnModel>();
            for(Article articleItem : articles) {
                List<UserVoteState> userVoteStates = userVoteStateService.retrieveByArticleId(articleItem.getId());

                Integer voteScore = 0;

                for(UserVoteState uvsItem : userVoteStates) {
                    voteScore = voteScore + uvsItem.getVoteState();
                }

                ArticleItemReturnModel articleItemReturnModel = new ArticleItemReturnModel(articleItem, voteScore);

                articleItemReturnModels.add(articleItemReturnModel);
            }

            pageOfArticleModel.setNumberOfPages(noPage);
            pageOfArticleModel.setCurrentPage(pageNum);
            pageOfArticleModel.setArticles(articleItemReturnModels);

            return new ResponseEntity<>(pageOfArticleModel, HttpStatus.OK);
        } else {
            List<Article> articles = articleService.retrieveOnePageByCategory(pageNum, categoryName);

            Integer noPage = (int)Math.ceil(Double.valueOf(articleService.retrieveNumOfPages(categoryName).intValue()) / 10);

            List<ArticleItemReturnModel> articleItemReturnModels = new ArrayList<ArticleItemReturnModel>();
            for(Article articleItem : articles) {
                List<UserVoteState> userVoteStates = userVoteStateService.retrieveByArticleId(articleItem.getId());

                Integer voteScore = 0;

                for(UserVoteState uvsItem : userVoteStates) {
                    voteScore = voteScore + uvsItem.getVoteState();
                }

                ArticleItemReturnModel articleItemReturnModel = new ArticleItemReturnModel(articleItem, voteScore);

                articleItemReturnModels.add(articleItemReturnModel);
            }

            pageOfArticleModel.setNumberOfPages(noPage);
            pageOfArticleModel.setCurrentPage(pageNum);
            pageOfArticleModel.setArticles(articleItemReturnModels);

            return new ResponseEntity<>(pageOfArticleModel, HttpStatus.OK);
        }
    }

    @GetMapping (
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveOneArticle(@PathVariable("id") Integer id) {
        Article article = articleService.retrieveOne(id);

        if (article != null) {
            return new ResponseEntity<>(article, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> createOneArticle(@RequestBody ArticleCreateModel article, @RequestHeader("Authorization") String authorization) {
        ResponseEntity<Object> entity;

        String token = authHeaderProcessing.getTokenFromAuthHeader(authorization);

        myJWT jwt = new jwtSecurity();

        Boolean authorized = jwt.VerifyToken(token, article.getAuthor());
        

        if(authorized) {
            if (article.getAuthor() == null ||
                article.getCategory() == null ||
                article.getContent() == null ||
                // article.getDateCreated() == null ||
                article.getDescription() == null ||
                article.getThumbnailUrl() == null ||
                // article.getTimeCreated() == null ||
                article.getTitle() == null) // ||
                /* article.getUrl() == null) */ {
                
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
            } else {
                Article articleEntity = article.toArticle(myDateTimeUtils.getCurrentDate(), myDateTimeUtils.getCurrentTime(), myUriParserUtils.getFinalArticleUrl(article.getTitle()));

                Article tmpToSave = articleService.createOne(articleEntity);

                if (tmpToSave == null) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
                } else {
                    entity = new ResponseEntity<>(tmpToSave, HttpStatus.CREATED);
                }
            }
        } else {
            entity = new ResponseEntity<>("{ \"Notice\": \"Unauthorized\" }", HttpStatus.UNAUTHORIZED);
        }

        return entity;
    }
    
    @PutMapping(
        value="/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> updateOneArticle(@PathVariable("id") Integer id, @RequestBody ArticleUpdateModel article, @RequestHeader("Authorization") String authorization) {
        
        ResponseEntity<Object> entity;

        String token = authHeaderProcessing.getTokenFromAuthHeader(authorization);

        myJWT jwt = new jwtSecurity();

        Boolean authorized = jwt.VerifyToken(token, article.getUserAgent());

        if(authorized) {
            if (/* article.getAuthor() == null || */
                article.getCategory() == null ||
                article.getContent() == null ||
                article.getAudioContent() == null ||
                // article.getDateCreated() == null ||
                article.getDescription() == null ||
                article.getThumbnailUrl() == null ||
                // article.getTimeCreated() == null ||
                article.getTitle() == null) // ||
                /* article.getUrl() == null) */ {
                
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
            } else {
                Article tmpArticle = articleService.retrieveOne(id);

                if(tmpArticle != null) {
                    Article articleEntity = article.toArticle(id, tmpArticle.getDateCreated(), tmpArticle.getTimeCreated(), tmpArticle.getAuthor(), tmpArticle.getUrl());

                    Article tmpToSave = articleService.updateOne(articleEntity);

                    entity = new ResponseEntity<>(tmpToSave, HttpStatus.OK);
                } else {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
                }
            }
        } else {
            entity = new ResponseEntity<>("{ \"Notice\": \"Unauthorized\" }", HttpStatus.UNAUTHORIZED);
        }

        return entity;
    }

    @DeleteMapping(
        value="/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> deleteOneArticle(@PathVariable("id") Integer id, @RequestHeader("Authorization") String authorization) {
        ResponseEntity<Object> entity;

        // String token = authHeaderProcessing.getTokenFromAuthHeader(authorization);

        // myJWT jwt = new jwtSecurity();

        // Boolean authorized = jwt.VerifyToken(token, )
        // processing authorization here

        Boolean kk = false;

        kk = commentService.deleteAllByArticleId(id);

        kk = userVoteStateService.deleteAllByArticleId(id);

        kk = articleReportService.deleteAllByArticleId(id);
        
        kk = articleService.deleteOne(id);

        if(kk) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Deleted\" }", HttpStatus.OK);
        } else {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
        }

        return entity;
    }

    /**
     * For comments
     */

    @GetMapping(
        value = "/{articleId}/comments",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveAllCommentsOfArticle(@PathVariable("articleId") Integer articleId) {
        ResponseEntity<Object> entity;

        List<Comment> comments;

        comments = commentService.retrieveAllByArticleId(articleId);

        entity = new ResponseEntity<>(comments, HttpStatus.OK);

        return entity;
    }

    @PostMapping(
        value = "/{articleId}/comments"
    )
    public ResponseEntity<Object> createOneCommentOfArticle(@PathVariable("articleId") Integer articleId, @RequestBody CommentCreateModel comment, @RequestHeader("Authorization") String authorization) {
        ResponseEntity<Object> entity;

        String token = authHeaderProcessing.getTokenFromAuthHeader(authorization);

        myJWT jwt = new jwtSecurity();

        Boolean authorized = jwt.VerifyToken(token, comment.getAuthor());

        if(authorized) {
            if (comment.getAuthor() == null ||
                comment.getContent() == null) { // ||
                // comment.getDate() == null ||
                // comment.getTime() == null) {
            
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
            } else {
                Comment commentEntity = comment.toComment(articleId, myDateTimeUtils.getCurrentDate(), myDateTimeUtils.getCurrentTime());

                Comment tmpToSave = commentService.createOne(commentEntity);

                if (tmpToSave == null) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
                } else {
                    entity = new ResponseEntity<>(tmpToSave, HttpStatus.CREATED);
                }
            }
        } else {
            entity = new ResponseEntity<>("{ \"Notice\": \"Unauthorized\" }", HttpStatus.UNAUTHORIZED);
        }
        return entity;
    }

    @PutMapping(
        value = "/{articleId}/comments/{commentId}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> updateOneCommentOfArticle(@PathVariable("articleId") Integer articleId, @PathVariable("commentId") Integer commentId, @RequestBody CommentUpdateModel comment, @RequestHeader("Authorization") String authorization) {
        ResponseEntity<Object> entity;

        String token = authHeaderProcessing.getTokenFromAuthHeader(authorization);

        myJWT jwt = new jwtSecurity();

        Boolean authorized = jwt.VerifyToken(token, comment.getAuthor());

        if(authorized) {
            if (comment.getAuthor() == null ||
                comment.getContent() == null) { // ||
                // comment.getDate() == null ||
                // comment.getTime() == null) {
            
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
            } else {
                Comment tmpLoad = commentService.retrieveOne(commentId);

                if(tmpLoad == null) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
                } else {
                    if(tmpLoad.getArticleId() != articleId) {
                        entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
                    } else {
                        Comment commentEntity = comment.toComment(articleId, commentId, myDateTimeUtils.getCurrentDate(), myDateTimeUtils.getCurrentTime());

                        Comment tmpToSave = commentService.updateOne(commentEntity);

                        if(tmpToSave == null) {
                            entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
                        } else {
                            entity = new ResponseEntity<>(tmpToSave, HttpStatus.OK);
                        }
                    }
                }
            }
        } else {
            entity = new ResponseEntity<>("{ \"Notice\": \"Unauthorized\" }", HttpStatus.UNAUTHORIZED);
        }

        return entity;
    }

    @DeleteMapping(
        value = "/{articleId}/comments/{commentId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> deleteOneCommentOfArticle(@PathVariable("articleId") Integer articleId, @PathVariable("commentId") Integer commentId) {
        ResponseEntity<Object> entity;

        Comment tmpLoad = commentService.retrieveOne(commentId);

        if(tmpLoad == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
        } else {
            if(tmpLoad.getArticleId() != articleId) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
            } else {
                Boolean kk = false;

                kk = commentService.deleteOne(commentId);

                if(kk) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Deleted\" }", HttpStatus.OK);
                } else {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
                }
            }
        }

        return entity;
    }

    /**
     * For Vote - Evaluation
     */

    @GetMapping(
        value = "/{articleId}/vote-state",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveOneUserVoteState(@PathVariable("articleId") Integer articleId, @RequestParam(value = "username", required = true) String username) {
        ResponseEntity<Object> entity;

        UserVoteState userVoteState = userVoteStateService.retrieveOneByArticleIdAndUsername(articleId, username);

        if(userVoteState == null) {
            UVSReturnModel uvsReturnModel = new UVSReturnModel(articleId, username, 0);

            entity = new ResponseEntity<>(uvsReturnModel, HttpStatus.OK);
        } else {
            UVSReturnModel uvsReturnModel = new UVSReturnModel(userVoteState);

            entity = new ResponseEntity<>(uvsReturnModel, HttpStatus.OK);
        }

        return entity;
    }

    @PutMapping(
        value = "/{articleId}/vote-state",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> updateUserVoteState(@PathVariable("articleId") Integer articleId, @RequestParam(value = "username", required = true) String username, @RequestBody UVSUpdateModel uvsUpdateModel) {
        ResponseEntity<Object> entity;

        if(uvsUpdateModel.getVoteState() == null) {

            entity = new ResponseEntity<>("{ \"Notice\": \"Vote state not be empty\" }", HttpStatus.BAD_REQUEST);

        } else if(uvsUpdateModel.getVoteState() < -1 || uvsUpdateModel.getVoteState() > 1) {

            entity = new ResponseEntity<>("{ \"Notice\": \"Vote state is invalid\" }", HttpStatus.BAD_REQUEST);

        } else {
            UserVoteState tmpUVS = userVoteStateService.retrieveOneByArticleIdAndUsername(articleId, username);

            if(tmpUVS != null) {
                UserVoteState uvsToSave = new UserVoteState(tmpUVS.getId(), tmpUVS.getArticleId(), tmpUVS.getUsername(), uvsUpdateModel.getVoteState());

                UserVoteState tmpSaved = userVoteStateService.updateOne(uvsToSave);

                if(tmpSaved == null) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Failed to save\" }", HttpStatus.BAD_REQUEST);
                } else {
                    entity = new ResponseEntity<>(tmpSaved, HttpStatus.OK);
                }
            } else {
                UserVoteState uvsToSave = new UserVoteState(0, articleId, username, uvsUpdateModel.getVoteState());

                UserVoteState tmpSaved = userVoteStateService.createOne(uvsToSave);

                if(tmpSaved == null) {
                    entity = new ResponseEntity<>("{ \"Notice\": \"Failed to save\" }", HttpStatus.BAD_REQUEST);
                } else {
                    entity = new ResponseEntity<>(tmpSaved, HttpStatus.OK);
                }
            }
        }

        return entity;
    }
}
