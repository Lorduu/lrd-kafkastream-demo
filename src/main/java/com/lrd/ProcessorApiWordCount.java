//package com.lrd;
//
//import org.apache.kafka.streams.KeyValue;
//import org.apache.kafka.streams.processor.Processor;
//import org.apache.kafka.streams.processor.ProcessorContext;
//import org.apache.kafka.streams.processor.internals.Punctuator;
//import org.apache.kafka.streams.state.KeyValueIterator;
//import org.apache.kafka.streams.state.KeyValueStore;
//
//public class ProcessorApiWordCount implements Processor<String, String> {
//    private ProcessorContext context;
//    private KeyValueStore<String, Long> kvStore;
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public void init(ProcessorContext context) {
//// keep the processor context locally because we need it in punctuate() and commit()
//        this.context = context;
//
//// schedule a punctuation method every 1000 milliseconds.
//        this.context.schedule(1000, PunctuationType.WALL_CLOCK_TIME, new Punctuator() {
//            @Override
//            public void punctuate(long timestamp) {
//                KeyValueIterator<String, Long> iter = this.kvStore.all();
//
//                while (iter.hasNext()) {
//                    KeyValue<String, Long> entry = iter.next();
//                    context.forward(entry.key, entry.value.toString());
//                }
//
//                // it is the caller's responsibility to close the iterator on state store;
//                // otherwise it may lead to memory and file handlers leak depending on the
//                // underlying state store implementation.
//                iter.close();
//
//                // commit the current processing progress
//                context.commit();
//            }
//        });
//
//// retrieve the key-value store named "Counts"
//        this.kvStore = (KeyValueStore<String, Long>) context.getStateStore("Counts");
//    }
//
//    @Override
//    public void process(String dummy, String line) {
//        String[] words = line.toLowerCase().split(" ");
//
//        for (String word : words) {
//            Long oldValue = this.kvStore.get(word);
//
//            if (oldValue == null) {
//                this.kvStore.put(word, 1L);
//            } else {
//                this.kvStore.put(word, oldValue + 1L);
//            }
//        }
//    }
//
//    @Override
//    public void punctuate(long timestamp) {
//        KeyValueIterator<String, Long> iter = this.kvStore.all();
//
//        while (iter.hasNext()) {
//            KeyValue<String, Long> entry = iter.next();
//            context.forward(entry.key, entry.value.toString());
//        }
//
//        iter.close(); // avoid OOM
//// commit the current processing progress
//        context.commit();
//    }
//
//    @Override
//    public void close() {
//// close any resources managed by this processor.
//// Note: Do not close any StateStores as these are managed
//// by the library
//    }
//}