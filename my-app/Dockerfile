FROM node:18-alpine
WORKDIR /myapp
COPY package.json .
COPY package-lock.json .
RUN npm install
COPY ./ ./
RUN npm run build
EXPOSE 5173
CMD ["npm", "run", "dev"]