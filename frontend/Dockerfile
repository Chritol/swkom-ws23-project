FROM nginx
COPY frontend /usr/share/nginx/html
COPY frontend/cors.conf /etc/nginx/conf.d/cors.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]