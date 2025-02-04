# etapa de producción
FROM nginx:1.15.8-alpine as production-stage
COPY ./spa /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]