FROM node:18 as BUILD
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alphine 
COPY --from=BUILD /app/dist  /usr/share/nginx/html
EXPOSE 90

