import React, { Component } from "react";
import PaginationBar from '../../../features/Pagination/index';
import ArticleCard from '../../../features/ArticleCard/index';

class ArticlesList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 1
        };
    }

    // componentDidMount() {
        // const {articlesCrude} = this.props;

        // console.log("Meow did mount: ", articlesCrude);

        // const listItems = articlesList.map((item) =>
        //     // <ArticleCard
        //     //     key={item.id}
        //     //     thumbnailUrl={item.thumbnailUrl}
        //     //     title={item.title}
        //     //     author={item.author}
        //     //     description={item.description}
        //     //     date={item.dateCreated}
        //     //     aUrl={"/" + item.url}
        //     // />
        //     <ArticleCard key={item.id} article={item} />
        // );
    // }

    receivePage = (indexPage) => {
        // alert("KKKK: " + indexPage);
        this.setState({
            page: indexPage
        });
        this.props.onHandleChange(indexPage);
    }

    render() {

        const {articlesCrude} = this.props;
        const listItems = articlesCrude.articles.map((item) => 
            <ArticleCard key={item.id} article={item} />
        );

        return (
            <div>
                {listItems}

                <PaginationBar
                    numberOfPages={articlesCrude.numberOfPages}
                    currentPage={articlesCrude.currentPage}
                    onHandleChange={this.receivePage}
                />
            </div>
        )
    }
}


export default ArticlesList;