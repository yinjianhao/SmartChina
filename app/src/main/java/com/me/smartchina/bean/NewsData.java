package com.me.smartchina.bean;

import java.util.ArrayList;

/**
 * @auther yjh
 * @date 2016/8/15
 */
public class NewsData {
    private int retcode;
    private ArrayList<NewsInnerData> data;

    public class NewsInnerData {
        private int id;
        private String title;
        private String type;
        private ArrayList<NewsTabData> children;

        public class NewsTabData {
            private int id;
            private String title;
            private String type;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ArrayList<NewsTabData> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<NewsTabData> children) {
            this.children = children;
        }
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public ArrayList<NewsInnerData> getData() {
        return data;
    }

    public void setData(ArrayList<NewsInnerData> data) {
        this.data = data;
    }
}
