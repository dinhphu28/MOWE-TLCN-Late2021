import React, { useEffect, useState } from 'react';
import { Card, CardBody, CardLink, CardSubtitle, CardText, CardTitle } from 'reactstrap';
// import PropTypes from 'prop-types';
import "./ReportMan.css";
import reportsApi from '../../api/reportsApi';
import LIArticleReportPopup from './ListArticleReportsPopup/index';

// ReportMan.propTypes = {};

function ReportMan(props) {

    const [listReport, setListReport] = useState([]);
    const [loaded, setLoaded] = useState(false);
    const [popupOpen, setPopupOpen] = useState(false);
    const [popupData, setPopupData] = useState({});

    useEffect(() => {
        const fetchListReports = async () => {
            try {
                const response = await reportsApi.getAll();

                // console.log("Fetch list reports successfully: ", response);

                setListReport(response);

                setLoaded(true);

            } catch (error) {
                console.log("Failed to fetch list reports: ", error);
            }
        }

        fetchListReports();
    }, []);

    const loadListReportCards = () => {
        if(loaded) {
            const listItems = listReport.map((item) =>
            <div key={item.id} className="my-report-card">
                <Card
                >
                    <CardBody>
                    <CardTitle tag="h5">
                        Article's title: {item.articleTitle}
                    </CardTitle>
                    <CardSubtitle
                        className="mb-2 text-muted"
                        tag="h6"
                    >
                        Report by: {item.author}
                    </CardSubtitle>
                    <CardText>
                        <small className='text-muted'>
                            Date: {item.date} Time: {item.time}
                        </small>
                    </CardText>
                    <CardText>
                        {item.content}
                    </CardText>
                    <CardLink
                        href='#'
                        onClick={() => {
                            const pData = {
                                articleId: item.articleId,
                                articleTitle: item.articleTitle
                            };
                            setPopupData(pData);
                            setPopupOpen(true);
                        }}
                    >
                        Show all reports of this article
                    </CardLink>
                    <CardLink href={"/articles/" + item.articleUrl}>
                        Go to article
                    </CardLink>
                    {/* <Button>
                        Button
                    </Button> */}
                    </CardBody>
                </Card>
            </div>
            );

            return listItems;
        }
    }

    const receiveCancel = () => {
        setPopupOpen(false);
    }

    return (
        <div className="my-reports-list">
            {loadListReportCards()}

            {popupOpen ? <LIArticleReportPopup
                onHandleChange={receiveCancel}
                articleId={popupData.articleId}
                articleTitle={popupData.articleTitle}
            />: ""}
        </div>
    );
}

export default ReportMan;