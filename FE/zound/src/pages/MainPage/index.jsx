import React, { useEffect, useState } from 'react';
import { Route, Routes } from 'react-router-dom';
import articleApi from '../../api/articleApi.js';
import NavMenu from './navMenu.jsx/index.jsx';
import ArticlesList from './ArticlesList/index.jsx';
import ArticleContent from '../../features/ArticleContent/index.jsx';
import { ARTICLE_CATEGORIES } from '../../constants/global.js';
// const ArticlesList = React.lazy(() => import('./ArticlesList/index.jsx'));

// import PropTypes from 'prop-types';

// MainPage.propTypes = {};

function MainPage(props) {

    const [page, setPage] = useState(0);
    const [category, setCategory] = useState(ARTICLE_CATEGORIES.default)
    const [articlesCrude, setArticlesCrude] = useState([]);
    const [loaded, setLoaded] = useState(false);

    useEffect(() => {
        const fetchArticleCrude = async () => {
            try {
                const params = {page: page, category: category.queryValue};

                const response = await articleApi.getAll(params);

                // console.log("Fetch article crude successfully", response);

                const data = response;

                setArticlesCrude(data);

                // console.log("These are articles list: ", data);

                setLoaded(true);

            } catch(error) {
                // throw Promise;
                console.log("Failed to fetch article crude: ", error);
            }
        }
        fetchArticleCrude();
    }, [category.queryValue, page]);

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

    //     // <li key={item.id}>{item.title}</li>
    // );

    // const listRoutes = articlesList.map((item) =>
    //     <Route path={"/articles/" + item.url} element={<SignInPage />}/>
    // );

    // const listRoute = articlesCrude.articles.map((item) =>
    //     <Route key={item.id} path={item.url} element={<ArticleContent article={item} />} />
    // );

    const loadListRoute = () => {
        if(loaded) {
            const listItems = articlesCrude.articles.map((item) =>
                <Route key={item.id} path={item.url} element={<ArticleContent article={item} />} />
                // <Route key={item.id} path={item.url} element={<ArticleContent
                //     title="Tieu de cung nhe"
                //     author={item.author}
                //     content={item.content}
                //     dateCreated={item.dateCreated}
                // />} />
            );

            return listItems;
        }
    };



    const receivePage = (indexPage) => {
        setPage(indexPage);
    };

    // let navigate = useNavigate();

    const receiveCategory = (categoryName) => {
        setPage(0);
        setCategory(categoryName);
    };

    return (

        <div>
            <NavMenu onHandleChange={receiveCategory}/>

            {/* {loading ? <ArticlesList articlesCrude={articlesCrude} /> : ""} */}

            <Routes>
                {/* <Route path="/meo" element={<SignUpPage />} /> */}
                {/* {listRoutes} */}
                {/* <Route path="*" element={<NotFound />} /> */}

                <Route
                    path="/"
                    element={
                        loaded ? <ArticlesList
                            articlesCrude={articlesCrude}
                            // page={page}
                            onHandleChange={receivePage}
                        /> : <p>Loading...</p>}
                />

                {loadListRoute()}

            </Routes>
        </div>
    );
}

export default MainPage;