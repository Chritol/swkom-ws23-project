FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

RUN apk --no-cache add maven tesseract-ocr leptonica tesseract-ocr-data-eng

ENV TESSDATA_PREFIX=/usr/share/tessdata/

COPY paperlessOCR /app

RUN mvn package -DskipTests

EXPOSE 6969
ENTRYPOINT ["java","-jar","target/paperlessOCR-0.0.1-SNAPSHOT.jar"]