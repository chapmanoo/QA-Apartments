FROM node:9.4.0
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
ENV PATH /usr/src/app/node_modules/.bin:$PATH
COPY package*.json /usr/src/app/
RUN npm install --silent
RUN npm install react-script@1.1.0 -g --silent
COPY . /usr/src/app/
EXPOSE 8080
CMD [ "npm", "start" ]
