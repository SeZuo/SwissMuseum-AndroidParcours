/*
 * Copyright 2012-2013 Sebastien Zurfluh
 * 
 * This file is part of "Swiss Museum Guides".
 * 
 * "Swiss Museum Guides" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "Swiss Museum Guides" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with "Swiss Museum Guides".  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.sebastienzurfluh.swissmuseum.panneauinteractif.model;

import android.provider.BaseColumns;

/**
 * This is the structure of the database, table by table.
 * 
 * This contract is used by the local SQLite3 implementation, and by the remote MySQL
 * implementation.
 *
 *
 * @author Sebastien Zurfluh
 *
 */
public abstract class DataProviderContract {
	private static final String POINT = ".";
	
	public static abstract class AffiliationsContract implements BaseColumns {
		public static final String TABLE_NAME = "affiliations";
		/**
		 * This is the name of a row element according to cake... this should not be part of
		 * the contract but cake's queries are tricky.
		 */
		public static final String ROW_ELEMENT_NAME = "affiliations";
		
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_PAGE_ID =  "page_id";
		public static final String COLUMN_NAME_GROUP_ID = "group_id";
		public static final String COLUMN_NAME_ORDER = "`order`";
        public static final String COLUMN_TRIMMED_NAME_ORDER = "order";
		
		public static final String ID = TABLE_NAME + POINT + COLUMN_NAME_ID;
		public static final String PAGE_ID = TABLE_NAME + POINT + COLUMN_NAME_PAGE_ID;
		public static final String GROUP_ID = TABLE_NAME + POINT + COLUMN_NAME_GROUP_ID; 
		public static final String ORDER = TABLE_NAME + POINT + COLUMN_NAME_ORDER;
	}
	
	public static abstract class GroupsContract implements BaseColumns {
		public static final String TABLE_NAME = "groups";
		/**
		 * This is the name of a row element according to cake... this should not be part of
		 * the contract but cake's queries are tricky.
		 */
		public static final String ROW_ELEMENT_NAME = "Group";
		
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_MENU_ID = "menu_id";
		
		public static final String ID = TABLE_NAME + POINT + COLUMN_NAME_ID;
		public static final String NAME = TABLE_NAME + POINT + COLUMN_NAME_NAME;
		public static final String MENU_ID = TABLE_NAME + POINT + COLUMN_NAME_MENU_ID;
	}
	
	public static abstract class MenusContract implements BaseColumns {
		public static final String TABLE_NAME = "menus";
		/**
		 * This is the name of a row element according to cake... this should not be part of
		 * the contract but cake's queries are tricky.
		 */
		public static final String ROW_ELEMENT_NAME = "Menu";
		
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		public static final String COLUMN_NAME_THUMB_IMG_URL = "thumb_img_url";
		public static final String COLUMN_NAME_IMG_URL = "img_url";

		public static final String ID = TABLE_NAME + POINT + COLUMN_NAME_ID;
		public static final String TITLE = TABLE_NAME + POINT + COLUMN_NAME_TITLE;
		public static final String DESCRIPTION = TABLE_NAME + POINT + COLUMN_NAME_DESCRIPTION;
		public static final String THUMB_IMG_URL = TABLE_NAME + POINT + COLUMN_NAME_THUMB_IMG_URL;
		public static final String IMG_URL = TABLE_NAME + POINT + COLUMN_NAME_IMG_URL;
	}
	
	public static abstract class PagesContract implements BaseColumns {
		/**
		 *  Cake CANNOT use the 'pages' name for a table. It is called 'page_elements' instead.
		 */
		public static final String TABLE_NAME = "page_elements";
		/**
		 * This is the name of a row element according to cake... this should not be part of
		 * the contract but cake's queries are tricky.
		 */
		public static final String ROW_ELEMENT_NAME = "PageElement";

		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_SUBTITLE = "subtitle";
		public static final String COLUMN_NAME_CONTENT = "content";
		public static final String COLUMN_NAME_MENU_ID = "menu_id";

		public static final String ID = TABLE_NAME + POINT + COLUMN_NAME_ID; 
		public static final String TITLE = TABLE_NAME + POINT + COLUMN_NAME_TITLE;
		public static final String SUBTITLE = TABLE_NAME + POINT + COLUMN_NAME_SUBTITLE;
		public static final String CONTENT = TABLE_NAME + POINT + COLUMN_NAME_CONTENT;
		public static final String MENU_ID = TABLE_NAME + POINT + COLUMN_NAME_MENU_ID;
	}

	public static abstract class ResourcesContract implements BaseColumns {
		public static final String TABLE_NAME = "resources";
		/**
		 * This is the name of a row element according to cake... this should not be part of
		 * the contract but cake's queries are tricky.
		 */
		public static final String ROW_ELEMENT_NAME = "Resource";
		
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_URL = "url";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		public static final String COLUMN_NAME_TYPE = "type";
		
		public static final String ID = TABLE_NAME + POINT + COLUMN_NAME_ID; 
		public static final String TITLE = TABLE_NAME + POINT + COLUMN_NAME_TITLE;
		public static final String URL = TABLE_NAME + POINT + COLUMN_NAME_URL;
		public static final String DESCRIPTION = TABLE_NAME + POINT + COLUMN_NAME_DESCRIPTION;
		public static final String TYPE = TABLE_NAME + POINT + COLUMN_NAME_TYPE;
	}
}
