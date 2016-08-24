package com.me.smartchina.bean;

import java.util.ArrayList;

/**
 * 新闻列表数据
 */
public class NewsListData {

    private int retcode;
    private NewsTab data;

    public class NewsTab {
        private ArrayList<TopNews> topnews;
        private ArrayList<News> news;
        private String title;
        private String more;

        public ArrayList<TopNews> getTopnews() {
            return topnews;
        }

        public void setTopnews(ArrayList<TopNews> topnews) {
            this.topnews = topnews;
        }

        public ArrayList<News> getNews() {
            return news;
        }

        public void setNews(ArrayList<News> news) {
            this.news = news;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public class TopNews {
            private String id;
            private String pubdate;
            private String title;
            private String topimage;
            private String url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPubdate() {
                return pubdate;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTopimage() {
                return topimage;
            }

            public void setTopimage(String topimage) {
                this.topimage = topimage;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public class News {
            private String id;
            private String pubdate;
            private String title;
            private String listimage;
            private String url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPubdate() {
                return pubdate;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getListimage() {
                return listimage;
            }

            public void setListimage(String listimage) {
                this.listimage = listimage;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public NewsTab getData() {
        return data;
    }

    public void setData(NewsTab data) {
        this.data = data;
    }
}
