


http://localhost:8075/article/add/
{"author":"author",
"content":"user",
"publishDate":"2020-10-10",
"title":"title"
}



http://localhost:8075/request/login
{"name":"user",
"password":"user"}
{"name":"admin",
"password":"admin"}



http://localhost:8075/article/list?pageNumber=0&pageSize=10&sortBy=title&sortDir=asc

pageNumber-0
articlePerPage-10
sortBy-title
sortDir - asc - desc

http://localhost:8075/admin/listArticles/?endpointDate=2020-10-10
