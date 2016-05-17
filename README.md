Simple chat for websockets techology acquisition.

index.jsp contains form for user's nickname and password entering
and binding it to new User instance. 
It is passed to Spring MVC controller MainController.java. 
Controller creates new ModelAndView linked to chat.jsp view page 
and passes User instance to it for user's nickname displaying in the header 
and in the chat window. 
chat.jsp also conteins js code providing connection 
to websocket endpoint in Chat.java.
