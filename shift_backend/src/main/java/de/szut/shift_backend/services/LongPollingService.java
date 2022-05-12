package de.szut.shift_backend.services;

import de.szut.shift_backend.model.dto.GetMessageDto;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class LongPollingService {

    private long timeOutInMilliSec = 10000L;

    public DeferredResult<String> keepMessagePolling(GetMessageDto getMessageDto) throws InterruptedException {
        DeferredResult<String> deferredResult = new DeferredResult<>(this.timeOutInMilliSec);
        CompletableFuture.runAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(10);
                deferredResult.setResult("adw"); //todo: hä?
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return deferredResult;
        /*
        Thread.sleep(10000);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/message"));
        return new ResponseEntity<>(httpHeaders, HttpStatus.TEMPORARY_REDIRECT);
        */
    }
}
