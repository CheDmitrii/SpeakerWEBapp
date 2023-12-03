package ru.dmitrii.speakerWEBapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dmitrii.speakerWEBapp.DAO.MusicDAO;
import ru.dmitrii.speakerWEBapp.models.User;

import java.io.*;


@Controller
@RequestMapping("/source")
public class SourceController {
    private User user;
    private MusicDAO musicDAO;

    @Autowired
    public SourceController(User user, MusicDAO musicDAO) {
        this.user = user;
        this.musicDAO = musicDAO;
    }


    @GetMapping("/music/{id}")
    public HttpEntity<byte[]> playAudio(@PathVariable("id") int id) throws FileNotFoundException {
        String path = musicDAO.getPathSong(id);
        long len = new File(path).length();
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(path));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(len);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);
    }

    @GetMapping("/foto/{id}")
    public HttpEntity<byte[]> showPicture(@PathVariable("id") int id) throws IOException {
        String path = musicDAO.getAlbumPicturePath(id);
        long length = new File(path).length();
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(path));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(length);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);
    }

    @GetMapping("/foto/artist/{id}")
    public HttpEntity<byte[]> showArtistFoto(@PathVariable("id") int id) throws IOException {
        File file = ResourceUtils.getFile("classpath:/image/artist/" + id + ".jpeg");
        long length = file.length();
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file.getAbsolutePath()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(length);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);
    }


    @GetMapping("/gif/{id}")
    public ResponseEntity<byte[]> getGif(@PathVariable("id") int id) throws IOException {

        File file = ResourceUtils.getFile("classpath:/image/gif/" + id + ".gif");

        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file.getAbsolutePath()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(file.length());
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.IMAGE_GIF);
        return new ResponseEntity(inputStreamResource, headers, HttpStatus.CREATED);

        // Second way to process source data
//        RandomAccessFile file = new RandomAccessFile("path/to/file.gif", "r");
//        byte[] bytes = new byte[(int)file.length()];
//        file.readFully(bytes);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_GIF);
//        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }


    @GetMapping("/resources/css/{code}.css")
    public ResponseEntity<String> styles(@PathVariable("code") String code) throws IOException {

        File file = ResourceUtils.getFile("classpath:/css/" + code + ".css");

        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file.getAbsolutePath()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamResource.getInputStream()));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line + "\n");
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/css; charset=utf-8");
        return new ResponseEntity<>(stringBuffer.toString(), headers, HttpStatus.OK);

//        InputStream is = getClass().getClassLoader().getResourceAsStream("classpath:/css/" + code + ".css");
//        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
//        StringBuffer sb = new StringBuffer();
//        String line = null;
//        while((line = bf.readLine()) != null){
//            sb.append(line + "\n");
//        }
//        final HttpHeaders httpHeaders= new HttpHeaders();
//        httpHeaders.add("Content-Type", "text/css; charset=utf-8");
//        return new ResponseEntity<String>( sb.toString(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/resources/js/{code}.js")
    public ResponseEntity<String> js(@PathVariable("code") String code) throws IOException {

        File file = ResourceUtils.getFile("classpath:/js/" + code + ".js");

        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file.getAbsolutePath()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamResource.getInputStream()));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line + "\n");
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/javascript; charset=utf-8");
        return new ResponseEntity<>(stringBuffer.toString(), headers, HttpStatus.OK);
    }
}
