package com.learn.library_management.config;

public class Messages {

	// ==================== Email Messages ====================
    public static final String RESET_PASSWORD = "Reset your password";
    public static final String RESEND_CODE = "A new code was sent";
    public static final String FAILED_EMAIL = "Failed to send email. Please try again later";
	
	// ==================== Auth Messages ==================== 
    public static final String LOGIN_SUCCESS = "Login successfully";
    public static final String CREATE_NEW_USER = "New user created successfully";
    public static final String LOGOUT_SUCCESS = "Logged out successfully";
    public static final String ALREADY_LOGGED_OUT = "You are already logged out";
    public static final String INVALID_PASSWORD = "Current password is incorrect";
    public static final String CHANGE_PASSWORD = "Password changed successfully";
    public static final String CODE_SENT = "Code sent successfully";
    public static final String INVALID_RESET_CODE = "Invalid reset code";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
    public static final String BAD_CREDENTIALS = "Invalid username or password";
    public static final String AUTH_FAILED = "Authentication failed";
    public static final String REQUEST_NOT_SUPPORTED = "Request method not supported";
    public static final String NOT_SALES_EMPLOYEE = "User does not have SALES_EMPLOYEE role.";
    public static final String NOT_PURCHASES_EMPLOYEE = "User does not have PURCHASING_OFFICER role.";
    
	// ==================== Token Messages ====================
    public static final String INVALID_REFRESH_TOKEN = "Invalid or missing refresh token";
    public static final String NEW_TOKEN_GENERATED = "New token generated successfully";
    public static final String COULD_NOT_EXTRACT_USER = "Unable to extract username from token";
    public static final String TOKEN_NOT_FOUND = "Token not found or invalid";
    public static final String TOKEN_EXPIRED_OR_REVOKED = "Token expired or revoked";
    public static final String SESSION_EXPIRED = "Your session has expired. Please login again";
    public static final String ACCESS_DENIED = "You do not have permission to access this resource";
    public static final String MISSING_TOKEN = "JWT token is missing";
    
	// ==================== User Messages ==================== 
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USERNAME_NOT_FOUND = "User not found with username or email: ";
    public static final String USER_NOT_AUTHENTICATED = "User not authenticated";
    public static final String CHANGE_ROLES_ERROR = "Invalid role. Please provide one of: ADMIN, STAFF, etc.";
    public static final String USER_UPDATE_PROFILE = "User role updated successfully";
    public static final String USER_UPDATE_IMAGE = "User image updated successfully";
    public static final String DELETE_USER = "User deleted successfully";
    public static final String UPDATE_USER = "User updated successfully";
    public static final String FETCH_USER = "User fetched successfully.";
    public static final String FETCH_USERS = "Users fetched successfully.";
    public static final String SEARCH_USER = "Users searched successfully.";

	// ==================== Error Messages ====================
    public static final String FORMAT_ERROR = "Malformed JSON request";
    public static final String INVALID_DATA = "Invalid data. Please check your request body";
    public static final String INVALID_ID_FORMAT = "Invalid ID format. Must be a numeric value.";
    
	// ==================== Category Messages ==================== 
    public static final String CATEGORY_NOT_FOUND = "Category not found";
    public static final String PARENT_CATEGORY_NOT_FOUND = "Parent category not found";
    public static final String CATEGORY_ALREADY_EXISTS = "Category with this name already exists";
    public static final String DELETE_CATEGORY = "Category deleted successfully";
    public static final String ADD_CATEGORY = "Category added successfully";
    public static final String UPDATE_CATEGORY = "Category updated successfully";
    public static final String FETCH_CATEGORIES = "All categories fetched successfully.";
    public static final String FETCH_ROOT_CATEGORIES = "Root categories fetched successfully.";
    public static final String FETCH_CATEGORY = "Category fetched successfully.";
    public static final String FETCH_SUBCATEGORIES = "Subcategories fetched successfully.";
    public static final String CANNOT_DELETE_SUBCATEGORY = "Cannot delete a subcategory";
    public static final String CANNOT_DELETE_CATEGORY_WITH_SUBCATEGORIES = "Cannot delete category with subcategories";
    public static final String CANNOT_DELETE_CATEGORY_WITH_BOOKS = "Cannot delete category with books";
    public static final String SEARCH_CATEGORIES = "categories searched successfully.";


    
    
	// ==================== Image Messages ====================
    public static final String EMPTY_IMAGE = "Image file is empty or null";
    public static final String UPLOAD_IMAGE_FAILED = "Error occurred while uploading image";
    

    // ==================== Book Messages ==================== 
    public static final String BOOK_NOT_FOUND = "Book not found";
    public static final String ADD_BOOK = "Book added successfully";
    public static final String UPDATE_BOOK = "Book updated successfully";
    public static final String DELETE_BOOK = "Book deleted successfully";
    public static final String FETCH_BOOK = "Book fetched successfully.";
    public static final String FETCH_BOOKS = "Books fetched successfully.";
    public static final String BOOK_ALREADY_EXISTS = "Book with same ISBN already exists";
    public static final String SEARCH_BOOKS = "Books searched successfully.";
    public static final String FETCH_BOOKS_BY_CATEGORY = "Books by category fetched successfully.";
    public static final String BOOK_HAS_TRANSACTIONS = "Cannot delete book because it has borrowing transactions.";


    // ==================== Member Messages ==================== 
    public static final String MEMBER_NOT_FOUND = "Member not found";
    public static final String ADD_MEMBER = "Member added successfully";
    public static final String UPDATE_MEMBER = "Member updated successfully";
    public static final String DELETE_MEMBER = "Member deleted successfully";
    public static final String FETCH_MEMBER = "Member fetched successfully.";
    public static final String FETCH_MEMBERS = "Members fetched successfully.";
    public static final String MEMBER_ALREADY_EXISTS = "Member already exists";
    public static final String SEARCH_MEMBERS = "Members searched successfully.";


    // ==================== Transaction Messages ==================== 
    public static final String BORROW_SUCCESS = "Book borrowed successfully";
    public static final String RETURN_SUCCESS = "Book returned successfully";
    public static final String TRANSACTION_NOT_FOUND = "Transaction not found";
    public static final String OVERDUE_FINE = "Fine applied due to overdue book";
    public static final String FETCH_TRANSACTION = "Transaction fetched successfully.";
    public static final String FETCH_TRANSACTIONS = "Transactions fetched successfully.";
    public static final String DELETE_TRANSACTION = "Transaction deleted successfully";



    // ==================== Author Messages ==================== 
    public static final String AUTHOR_NOT_FOUND = "Author not found";
    public static final String ADD_AUTHOR = "Author added successfully";
    public static final String UPDATE_AUTHOR = "Author updated successfully";
    public static final String DELETE_AUTHOR = "Author deleted successfully";
    public static final String AUTHOR_ALREADY_EXISTS = "Author already exists";
    public static final String FETCH_AUTHOR = "Author fetched successfully.";
    public static final String FETCH_AUTHORS = "Authors fetched successfully.";
    public static final String AUTHOR_HAS_BOOKS = "Cannot delete Authors with existing books";
    public static final String SEARCH_AUTHORS = "Authors searched successfully.";

    
    // ==================== Publisher Messages ==================== 
    public static final String PUBLISHER_NOT_FOUND = "Publisher not found";
    public static final String ADD_PUBLISHER = "Publisher added successfully";
    public static final String UPDATE_PUBLISHER = "Publisher updated successfully";
    public static final String DELETE_PUBLISHER = "Publisher deleted successfully";
    public static final String PUBLISHER_HAS_BOOKS = "Cannot delete publisher with existing books";
    public static final String FETCH_PUBLISHER = "Publisher fetched successfully.";
    public static final String FETCH_PUBLISHERS = "Publisher fetched successfully.";
    public static final String PUBLISHER_ALREADY_EXISTS = "Publisher already exists";
    public static final String SEARCH_PUBLISHERS = "Publishers searched successfully.";


    // ==================== Role Messages ====================
    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String ADD_ROLE = "Role added successfully";
    public static final String UPDATE_ROLE = "Role updated successfully";
    public static final String DELETE_ROLE = "Role deleted successfully";
    public static final String FETCH_ROLES = "Roles fetched successfully.";
    public static final String ROLE_ALREADY_EXISTS = "Rpublic class Messages {\n" +
            "\n" +
            "\t// ==================== Email Messages ====================\n" +
            "    public static final String RESET_PASSWORD = \"Reset your password\";\n" +
            "    public static final String RESEND_CODE = \"A new code was sent\";\n" +
            "    public static final String FAILED_EMAIL = \"Failed to send email. Please try again later\";\n" +
            "\t\n" +
            "\t// ==================== Auth Messages ==================== \n" +
            "    public static final String LOGIN_SUCCESS = \"Login successfully\";\n" +
            "    public static final String CREATE_NEW_USER = \"New user created successfully\";\n" +
            "    public static final String LOGOUT_SUCCESS = \"Logged out successfully\";\n" +
            "    public static final String ALREADY_LOGGED_OUT = \"You are already logged out\";\n" +
            "    public static final String INVALID_PASSWORD = \"Current password is incorrect\";\n" +
            "    public static final String CHANGE_PASSWORD = \"Password changed successfully\";\n" +
            "    public static final String CODE_SENT = \"Code sent successfully\";\n" +
            "    public static final String INVALID_RESET_CODE = \"Invalid reset code\";\n" +
            "    public static final String EMAIL_ALREADY_EXISTS = \"Email already exists\";\n" +
            "    public static final String USERNAME_ALREADY_EXISTS = \"Username already exists\";\n" +
            "    public static final String BAD_CREDENTIALS = \"Invalid username or password\";\n" +
            "    public static final String AUTH_FAILED = \"Authentication failed\";\n" +
            "    public static final String REQUEST_NOT_SUPPORTED = \"Request method not supported\";\n" +
            "    public static final String NOT_SALES_EMPLOYEE = \"User does not have SALES_EMPLOYEE role.\";\n" +
            "    public static final String NOT_PURCHASES_EMPLOYEE = \"User does not have PURCHASING_OFFICER role.\";\n" +
            "    \n" +
            "\t// ==================== Token Messages ====================\n" +
            "    public static final String INVALID_REFRESH_TOKEN = \"Invalid or missing refresh token\";\n" +
            "    public static final String NEW_TOKEN_GENERATED = \"New token generated successfully\";\n" +
            "    public static final String COULD_NOT_EXTRACT_USER = \"Unable to extract username from token\";\n" +
            "    public static final String TOKEN_NOT_FOUND = \"Token not found or invalid\";\n" +
            "    public static final String TOKEN_EXPIRED_OR_REVOKED = \"Token expired or revoked\";\n" +
            "    public static final String SESSION_EXPIRED = \"Your session has expired. Please login again\";\n" +
            "    public static final String ACCESS_DENIED = \"You do not have permission to access this resource\";\n" +
            "    public static final String MISSING_TOKEN = \"JWT token is missing\";\n" +
            "    \n" +
            "\t// ==================== User Messages ==================== \n" +
            "    public static final String USER_NOT_FOUND = \"User not found\";\n" +
            "    public static final String USERNAME_NOT_FOUND = \"User not found with username or email: \";\n" +
            "    public static final String USER_NOT_AUTHENTICATED = \"User not authenticated\";\n" +
            "    public static final String CHANGE_ROLES_ERROR = \"Invalid role. Please provide one of: ADMIN, STAFF, etc.\";\n" +
            "    public static final String USER_UPDATE_PROFILE = \"User role updated successfully\";\n" +
            "    public static final String USER_UPDATE_IMAGE = \"User image updated successfully\";\n" +
            "    public static final String DELETE_USER = \"User deleted successfully\";\n" +
            "    public static final String UPDATE_USER = \"User updated successfully\";\n" +
            "    public static final String FETCH_USER = \"User fetched successfully.\";\n" +
            "    public static final String FETCH_USERS = \"Users fetched successfully.\";\n" +
            "    public static final String SEARCH_USER = \"Users searched successfully.\";\n" +
            "\n" +
            "\t// ==================== Error Messages ====================\n" +
            "    public static final String FORMAT_ERROR = \"Malformed JSON request\";\n" +
            "    public static final String INVALID_DATA = \"Invalid data. Please check your request body\";\n" +
            "    public static final String INVALID_ID_FORMAT = \"Invalid ID format. Must be a numeric value.\";\n" +
            "    \n" +
            "\t// ==================== Category Messages ==================== \n" +
            "    public static final String CATEGORY_NOT_FOUND = \"Category not found\";\n" +
            "    public static final String PARENT_CATEGORY_NOT_FOUND = \"Parent category not found\";\n" +
            "    public static final String CATEGORY_ALREADY_EXISTS = \"Category with this name already exists\";\n" +
            "    public static final String DELETE_CATEGORY = \"Category deleted successfully\";\n" +
            "    public static final String ADD_CATEGORY = \"Category added successfully\";\n" +
            "    public static final String UPDATE_CATEGORY = \"Category updated successfully\";\n" +
            "    public static final String FETCH_CATEGORIES = \"All categories fetched successfully.\";\n" +
            "    public static final String FETCH_ROOT_CATEGORIES = \"Root categories fetched successfully.\";\n" +
            "    public static final String FETCH_CATEGORY = \"Category fetched successfully.\";\n" +
            "    public static final String FETCH_SUBCATEGORIES = \"Subcategories fetched successfully.\";\n" +
            "    public static final String CANNOT_DELETE_SUBCATEGORY = \"Cannot delete a subcategory\";\n" +
            "    public static final String CANNOT_DELETE_CATEGORY_WITH_SUBCATEGORIES = \"Cannot delete category with subcategories\";\n" +
            "    public static final String CANNOT_DELETE_CATEGORY_WITH_BOOKS = \"Cannot delete category with books\";\n" +
            "    public static final String SEARCH_CATEGORIES = \"categories searched successfully.\";\n" +
            "\n" +
            "\n" +
            "    \n" +
            "    \n" +
            "\t// ==================== Image Messages ====================\n" +
            "    public static final String EMPTY_IMAGE = \"Image file is empty or null\";\n" +
            "    public static final String UPLOAD_IMAGE_FAILED = \"Error occurred while uploading image\";\n" +
            "    \n" +
            "\n" +
            "    // ==================== Book Messages ==================== \n" +
            "    public static final String BOOK_NOT_FOUND = \"Book not found\";\n" +
            "    public static final String ADD_BOOK = \"Book added successfully\";\n" +
            "    public static final String UPDATE_BOOK = \"Book updated successfully\";\n" +
            "    public static final String DELETE_BOOK = \"Book deleted successfully\";\n" +
            "    public static final String FETCH_BOOK = \"Book fetched successfully.\";\n" +
            "    public static final String FETCH_BOOKS = \"Books fetched successfully.\";\n" +
            "    public static final String BOOK_ALREADY_EXISTS = \"Book with same ISBN already exists\";\n" +
            "    public static final String SEARCH_BOOKS = \"Books searched successfully.\";\n" +
            "    public static final String FETCH_BOOKS_BY_CATEGORY = \"Books by category fetched successfully.\";\n" +
            "    public static final String BOOK_HAS_TRANSACTIONS = \"Cannot delete book because it has borrowing transactions.\";\n" +
            "\n" +
            "\n" +
            "    // ==================== Member Messages ==================== \n" +
            "    public static final String MEMBER_NOT_FOUND = \"Member not found\";\n" +
            "    public static final String ADD_MEMBER = \"Member added successfully\";\n" +
            "    public static final String UPDATE_MEMBER = \"Member updated successfully\";\n" +
            "    public static final String DELETE_MEMBER = \"Member deleted successfully\";\n" +
            "    public static final String FETCH_MEMBER = \"Member fetched successfully.\";\n" +
            "    public static final String FETCH_MEMBERS = \"Members fetched successfully.\";\n" +
            "    public static final String MEMBER_ALREADY_EXISTS = \"Member already exists\";\n" +
            "    public static final String SEARCH_MEMBERS = \"Members searched successfully.\";\n" +
            "\n" +
            "\n" +
            "    // ==================== Transaction Messages ==================== \n" +
            "    public static final String BORROW_SUCCESS = \"Book borrowed successfully\";\n" +
            "    public static final String RETURN_SUCCESS = \"Book returned successfully\";\n" +
            "    public static final String TRANSACTION_NOT_FOUND = \"Transaction not found\";\n" +
            "    public static final String OVERDUE_FINE = \"Fine applied due to overdue book\";\n" +
            "    public static final String FETCH_TRANSACTION = \"Transaction fetched successfully.\";\n" +
            "    public static final String FETCH_TRANSACTIONS = \"Transactions fetched successfully.\";\n" +
            "    public static final String DELETE_TRANSACTION = \"Transaction deleted successfully\";\n" +
            "\n" +
            "\n" +
            "\n" +
            "    // ==================== Author Messages ==================== \n" +
            "    public static final String AUTHOR_NOT_FOUND = \"Author not found\";\n" +
            "    public static final String ADD_AUTHOR = \"Author added successfully\";\n" +
            "    public static final String UPDATE_AUTHOR = \"Author updated successfully\";\n" +
            "    public static final String DELETE_AUTHOR = \"Author deleted successfully\";\n" +
            "    public static final String AUTHOR_ALREADY_EXISTS = \"Author already exists\";\n" +
            "    public static final String FETCH_AUTHOR = \"Author fetched successfully.\";\n" +
            "    public static final String FETCH_AUTHORS = \"Authors fetched successfully.\";\n" +
            "    public static final String AUTHOR_HAS_BOOKS = \"Cannot delete Authors with existing books\";\n" +
            "    public static final String SEARCH_AUTHORS = \"Authors searched successfully.\";\n" +
            "\n" +
            "    \n" +
            "    // ==================== Publisher Messages ==================== \n" +
            "    public static final String PUBLISHER_NOT_FOUND = \"Publisher not found\";\n" +
            "    public static final String ADD_PUBLISHER = \"Publisher added successfully\";\n" +
            "    public static final String UPDATE_PUBLISHER = \"Publisher updated successfully\";\n" +
            "    public static final String DELETE_PUBLISHER = \"Publisher deleted successfully\";\n" +
            "    public static final String PUBLISHER_HAS_BOOKS = \"Cannot delete publisher with existing books\";\n" +
            "    public static final String FETCH_PUBLISHER = \"Publisher fetched successfully.\";\n" +
            "    public static final String FETCH_PUBLISHERS = \"Publisher fetched successfully.\";\n" +
            "    public static final String PUBLISHER_ALREADY_EXISTS = \"Publisher already exists\";\n" +
            "    public static final String SEARCH_PUBLISHERS = \"Publishers searched successfully.\";\n" +
            "\n" +
            "\n" +
            "    // ==================== Role Messages ====================\n" +
            "    public static final String ROLE_NOT_FOUND = \"Role not found\";\n" +
            "    public static final String ADD_ROLE = \"Role added successfully\";\n" +
            "    public static final String UPDATE_ROLE = \"Role updated successfully\";\n" +
            "    public static final String DELETE_ROLE = \"Role deleted successfully\";\n" +
            "    public static final String FETCH_ROLES = \"Roles fetched successfully.\";\n" +
            "    public static final String ROLE_ALREADY_EXISTS = \"Role with this name already exists\";\n" +
            "\n" +
            "    // ==================== Logs Messages ====================\n" +
            "    public static final String FETCH_ALL_LOGS = \"All activity logs fetched successfully.\";\n" +
            "    public static final String FETCH_LOGS = \"User activity logs fetched successfully.\";\n" +
            "\n" +
            "    \n" +
            "}ole with this name already exists";

    // ==================== Logs Messages ====================
    public static final String FETCH_ALL_LOGS = "All activity logs fetched successfully.";
    public static final String FETCH_LOGS = "User activity logs fetched successfully.";

    
}