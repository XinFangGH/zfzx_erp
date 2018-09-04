package com.zhiwei.credit.model.p2p.article;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.DefinitionList;
import org.htmlparser.tags.DefinitionListBullet;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.google.gson.annotations.Expose;
import com.zhiwei.core.util.StringUtil;

/**
 * 
 * @author 
 *
 */
/**
 * Article Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class Article extends com.zhiwei.core.model.BaseModel {
	public static final int MAX_RECOMMEND_ARTICLE_LIST_COUNT = 20;// 推荐文章列表最大文章数
	public static final int MAX_HOT_ARTICLE_LIST_COUNT = 20;// 热点文章列表最大文章数
	public static final int MAX_NEW_ARTICLE_LIST_COUNT = 20;// 最新文章列表最大文章数
	public static final int MAX_PAGE_CONTENT_COUNT = 5000;// 内容分页每页最大字数
	public static final int DEFAULT_ARTICLE_LIST_PAGE_SIZE = 10;// 文章列表默认每页显示数
	public static final int DEFAULT_ARTICLE_POST_LIST_PAGE_SIZE=10; //公告文章列表默认每页显示数
    @Expose
	protected Long id;
    /**
     * 创建时间
     */
    @Expose
	protected java.util.Date createDate;
    /**
     * 修改时间
     */
    @Expose
	protected java.util.Date modifyDate;
    /**
     * 作者
     */
    @Expose
	protected String author;
    /**
     * 内容
     */
    @Expose
	protected  String content;
    /**
     * 点击数
     */
    @Expose
	protected Integer hits;
    /**
     * 文件路径
     */
    @Expose
	protected String htmlFilePath;
    /**
     * 是否发布 0否1是
     */
    @Expose
	protected String isPublication;
    /**
     * 是否推荐 0否1是
     */
    @Expose
	protected String isRecommend;
    /**
     * 是否置顶0否1是
     */
    @Expose
	protected String isTop;
    /**
     * 页面描述
     */
    @Expose
	protected String metaDescription;
    /**
     * 页面关键字
     */
    @Expose
	protected String metaKeywords;
    /**
     * 页数
     */
    @Expose
	protected Integer pageCount;
    /**
     * 标题
     */
    @Expose
	protected String title;
    /**
     * 是否单页面0否1是
     */
    @Expose
	protected Integer single;
    /**
     * 栏目简写
     */
    @Expose
    protected String col;
    @Expose
	protected com.zhiwei.credit.model.p2p.article.Articlecategory articlecategory;
    /**
     * 图片路径
     */
    @Expose
    protected String themeFtpUrl;
   
	public String getThemeFtpUrl() {
		return themeFtpUrl;
	}

	public void setThemeFtpUrl(String themeFtpUrl) {
		this.themeFtpUrl = themeFtpUrl;
	}

	/**
	 * Default Empty Constructor for class Article
	 */
	public Article () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Article
	 */
	public Article (
		 Long in_id
        ) {
		this.setId(in_id);
    }

	
	public String getCol() {
		return col;
	}
   /**
    * 单页面/文章页面名称  auout.html
    * @param col
    */
	public void setCol(String col) {
		this.col = col;
	}

	public com.zhiwei.credit.model.p2p.article.Articlecategory getArticlecategory () {
		return articlecategory;
	}	
	
	public void setArticlecategory (com.zhiwei.credit.model.p2p.article.Articlecategory in_articlecategory) {
		this.articlecategory = in_articlecategory;
	}
	

	public Integer getSingle() {
		return single;
	}
    /**
     * 0 非单页面
     * 1 单页面
     * @param single
     */
	public void setSingle(Integer single) {
		this.single = single;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="modifyDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getModifyDate() {
		return this.modifyDate;
	}
	
	/**
	 * Set the modifyDate
	 */	
	public void setModifyDate(java.util.Date aValue) {
		this.modifyDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="author" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Set the author
	 */	
	public void setAuthor(String aValue) {
		this.author = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="65535" not-null="true" unique="false"
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Set the content
	 * @spring.validator type="required"
	 */	
	public void setContent(String aValue) {
		this.content = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="hits" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getHits() {
		return this.hits;
	}
	
	/**
	 * Set the hits
	 * @spring.validator type="required"
	 */	
	public void setHits(Integer aValue) {
		this.hits = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="htmlFilePath" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getHtmlFilePath() {
		return this.htmlFilePath;
	}
	
	/**
	 * Set the htmlFilePath
	 */	
	public void setHtmlFilePath(String aValue) {
		this.htmlFilePath = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isPublication" type="java.lang.String" length="1" not-null="true" unique="false"
	 */
	public String getIsPublication() {
		return this.isPublication;
	}
	
	/**
	 * Set the isPublication
	 * @spring.validator type="required"
	 */	
	public void setIsPublication(String aValue) {
		this.isPublication = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isRecommend" type="java.lang.String" length="1" not-null="true" unique="false"
	 */
	public String getIsRecommend() {
		return this.isRecommend;
	}
	
	/**
	 * Set the isRecommend
	 * @spring.validator type="required"
	 */	
	public void setIsRecommend(String aValue) {
		this.isRecommend = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isTop" type="java.lang.String" length="1" not-null="true" unique="false"
	 */
	public String getIsTop() {
		return this.isTop;
	}
	
	/**
	 * Set the isTop
	 * @spring.validator type="required"
	 */	
	public void setIsTop(String aValue) {
		this.isTop = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="metaDescription" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getMetaDescription() {
		return this.metaDescription;
	}
	
	/**
	 * Set the metaDescription
	 */	
	public void setMetaDescription(String aValue) {
		this.metaDescription = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="metaKeywords" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getMetaKeywords() {
		return this.metaKeywords;
	}
	
	/**
	 * Set the metaKeywords
	 */	
	public void setMetaKeywords(String aValue) {
		this.metaKeywords = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="pageCount" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getPageCount() {
		return this.pageCount;
	}
	
	/**
	 * Set the pageCount
	 * @spring.validator type="required"
	 */	
	public void setPageCount(Integer aValue) {
		this.pageCount = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set the title
	 * @spring.validator type="required"
	 */	
	public void setTitle(String aValue) {
		this.title = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getArticleCategoryId() {
		return this.getArticlecategory()==null?null:this.getArticlecategory().getId();
	}
	
	/**
	 * Set the articleCategoryId
	 */	
	public void setArticleCategoryId(Long aValue) {
	    if (aValue==null) {
	    	articlecategory = null;
	    } else if (articlecategory == null) {
	        articlecategory = new com.zhiwei.credit.model.p2p.article.Articlecategory(aValue);
	        articlecategory.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			articlecategory.setId(aValue);
	    }
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Article)) {
			return false;
		}
		Article rhs = (Article) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.createDate, rhs.createDate)
				.append(this.modifyDate, rhs.modifyDate)
				.append(this.author, rhs.author)
				.append(this.content, rhs.content)
				.append(this.hits, rhs.hits)
				.append(this.htmlFilePath, rhs.htmlFilePath)
				.append(this.isPublication, rhs.isPublication)
				.append(this.isRecommend, rhs.isRecommend)
				.append(this.isTop, rhs.isTop)
				.append(this.metaDescription, rhs.metaDescription)
				.append(this.metaKeywords, rhs.metaKeywords)
				.append(this.pageCount, rhs.pageCount)
				.append(this.title, rhs.title)
				.append(this.themeFtpUrl, rhs.themeFtpUrl)
				
				
						.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.createDate) 
				.append(this.modifyDate) 
				.append(this.author) 
				.append(this.content) 
				.append(this.hits) 
				.append(this.htmlFilePath) 
				.append(this.isPublication) 
				.append(this.isRecommend) 
				.append(this.isTop) 
				.append(this.metaDescription) 
				.append(this.metaKeywords) 
				.append(this.pageCount) 
				.append(this.title) 
				.append(this.themeFtpUrl) 
				
						.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("createDate", this.createDate) 
				.append("modifyDate", this.modifyDate) 
				.append("author", this.author) 
				.append("content", this.content) 
				.append("hits", this.hits) 
				.append("htmlFilePath", this.htmlFilePath) 
				.append("isPublication", this.isPublication) 
				.append("isRecommend", this.isRecommend) 
				.append("isTop", this.isTop) 
				.append("metaDescription", this.metaDescription) 
				.append("metaKeywords", this.metaKeywords) 
				.append("pageCount", this.pageCount) 
				.append("title", this.title) 
				.append("themeFtpUrl", this.themeFtpUrl) 
				
						.toString();
	}

	// 获取文章分页内容
	@Transient
	public List<String> getPageContentList() {
		List<String> pageContentList = new ArrayList<String>();
		// 如果文章内容长度小于等于每页最大字符长度则直接返回所有字符
		if (content.length() <= Article.MAX_PAGE_CONTENT_COUNT) {
			pageContentList.add(content);
			return pageContentList;
		}
		NodeFilter tableFilter = new NodeClassFilter(TableTag.class);// TABLE
		NodeFilter divFilter = new NodeClassFilter(Div.class);// DIV
		NodeFilter paragraphFilter = new NodeClassFilter(ParagraphTag.class);// P
		NodeFilter bulletListFilter = new NodeClassFilter(BulletList.class);// UL
		NodeFilter bulletFilter = new NodeClassFilter(Bullet.class);// LI
		NodeFilter definitionListFilter = new NodeClassFilter(DefinitionList.class);// DL
		NodeFilter DefinitionListBulletFilter = new NodeClassFilter(DefinitionListBullet.class);// DD

		OrFilter orFilter = new OrFilter();
		orFilter.setPredicates(new NodeFilter[] { paragraphFilter, divFilter, tableFilter, bulletListFilter, bulletFilter, definitionListFilter, DefinitionListBulletFilter });
		List<Integer> indexList = new ArrayList<Integer>();
		// 按每页最大字符长度分割文章内容
		List<String> contentList = StringUtil.splitString(content, Article.MAX_PAGE_CONTENT_COUNT);
		for (int i = 0; i < contentList.size() - 1; i++) {
			try {
				String currentContent = contentList.get(i);
				Parser parser = Parser.createParser(currentContent, "UTF-8");
				NodeList nodeList = parser.parse(orFilter);
				if (nodeList.size() > 0) {
					// 若在此段内容中查找到相关标签，则记录最后一个标签的索引位置
					Node node = nodeList.elementAt(nodeList.size() - 1);
					indexList.add(i * Article.MAX_PAGE_CONTENT_COUNT + node.getStartPosition());
				} else {
					// 若在此段内容中未找到任何相关标签，则查找相关标点符号，并记录最后一个标点符号的索引位置
					String regEx = "\\.|。|\\!|！|\\?|？";
					Pattern pattern = Pattern.compile(regEx);
					Matcher matcher = pattern.matcher(currentContent);
					if (matcher.find()) {
						int endIndex = 0;
						while (matcher.find()) {
							endIndex = matcher.end();
						}
						indexList.add(i * Article.MAX_PAGE_CONTENT_COUNT + endIndex);
					} else {
						indexList.add((i + 1) * Article.MAX_PAGE_CONTENT_COUNT);
					}
				}
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i <= indexList.size(); i++) {
			String pageContent = "";
			if (i == 0) {
				pageContent = content.substring(0, indexList.get(0));
			} else if (i == indexList.size()) {
				pageContent = content.substring(indexList.get(i - 1));
			} else {
				pageContent = content.substring(indexList.get(i - 1), indexList.get(i));
			}
			try {
				// 对分割出的分页内容进行HTML标签补全
				Parser parser = Parser.createParser(pageContent, "UTF-8");
				NodeList nodeList = parser.parse(orFilter);
				String contentResult = nodeList.toHtml();
				if (StringUtils.isEmpty(contentResult)) {
					contentResult = pageContent;
				}
				pageContentList.add(contentResult);
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		return pageContentList;
	}


}
