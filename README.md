# Ads Management System

The system can help to manage ads request for users.

Ads request can be represented as Application. Any application has type (_website, android, ios_),
content (_html, image, video_), name and owner (_publisher_).

There are 3 roles of users: **admins**, **operators** and **publishers**.

>Users with ADMIN role can do following:
- CRUD operations with OPERATORs
- CRUD operations with PUBLISHERs


>User with OPERATOR role can do following:
- CRUD operations with PUBLISHERs
- CRUD operations with all applications


>User with PUBLISHER role can do following:
- CRUD operations with his own applications

