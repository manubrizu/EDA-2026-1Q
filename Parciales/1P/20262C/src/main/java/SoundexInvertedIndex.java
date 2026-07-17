import java.util.Scanner;

public class SoundexInvertedIndex {
    private IndexEntry[] index = new IndexEntry[1000];
    private int indexSize = 0;

    class IndexEntry {
        String soundexCode;
        // Arreglo preasignado para los documentos que contienen este Soundex
        DocFreq[] documents = new DocFreq[100];
        int docCount = 0;
    }

    class DocFreq {
        int docId;
        TermFreq[] terms = new TermFreq[100];
        int termCount = 0;      // Cantidad de términos distintos en el arreglo
        int totalFrequency = 0; // Suma de todas las frecuencias de los términos
    }

    class TermFreq {
        String term;
        int termFrequency = 0;
    }



    static private char getSoundexMapping( char in ){
        switch (in) {
            case 'B': case 'F': case 'P': case 'V': return '1';
            case 'C': case 'G': case 'J': case 'K': case 'Q': case 'S': case 'X': case 'Z': return '2';
            case 'D': case 'T': return '3';
            case 'L': return '4';
            case 'M': case 'N': return '5';
            case 'R': return '6';
        }

        return '0';
    }

    static private String getSoundexCode( String sIn ){
        char[] IN = sIn.toUpperCase().toCharArray();
        char[] OUT = { '0', '0', '0', '0'};
        OUT[0]=IN[0];
        int count = 1;
        char current = getSoundexMapping(IN[0]);
        char last = current;
        for (int i = 1; i < IN.length && count < 4; ++i, last=current) {
            char iter = IN[i];
            current = getSoundexMapping(iter);
            if (current != '0' && current != last) {
                OUT[count] = current;
                ++count;
            }
        }
        return new String(OUT);
    }

    public void dump() {
        for (int i = 0; i < indexSize; i++) {
            IndexEntry entry = index[i];
            System.out.println("Soundex: " + entry.soundexCode);
            for (int j = 0; j < entry.docCount; j++) {
                DocFreq doc = entry.documents[j];
                System.out.println("  DocID: " + doc.docId + " (Total Freq: " + doc.totalFrequency + ")");
                for (int k = 0; k < doc.termCount; k++) {
                    TermFreq tf = doc.terms[k];
                    System.out.println("    Term: " + tf.term + " - Freq: " + tf.termFrequency);
                }
            }
        }
    }


    public void addDocument( int docID, String content ){
        Scanner termScanner = new Scanner(content).useDelimiter("\\s+");

        while (termScanner.hasNext()) {
            String term = termScanner.next();
            String soundexCode = getSoundexCode(term);

            ///  INDEX ENTRY
            int indexPos = binarySearchIndex(soundexCode);
            IndexEntry entry;
            if (indexPos < indexSize && index[indexPos].soundexCode.equals(soundexCode)) {
                entry = index[indexPos];
            } else {
                if (indexSize >= 1000) throw new RuntimeException("LLENO");
                for (int i = indexSize; i > indexPos; i--) {       ///  MUEVO TODO PARA LA DERECHA Y DSP METO EN INDEXPOS
                    index[i] = index[i - 1];
                }
                entry = new IndexEntry();
                entry.soundexCode = soundexCode;
                index[indexPos] = entry;
                indexSize++;
            }

            ///  DOCFREQ
            int docPos = binarySearchDoc(entry.documents, entry.docCount, docID);
            DocFreq docFreq;
            if (docPos < entry.docCount && entry.documents[docPos].docId == docID) {
                docFreq = entry.documents[docPos];
            } else {
                if (entry.docCount >= 100) throw new RuntimeException("DocFreq LLENO");
                for (int i = entry.docCount; i > docPos; i--) {
                    entry.documents[i] = entry.documents[i - 1];
                }
                docFreq = new DocFreq();
                docFreq.docId = docID;
                entry.documents[docPos] = docFreq;
                entry.docCount++;
            }

            ///  TERMFREQ
            int termPos = binarySearchTerm(docFreq.terms, docFreq.termCount, term);
            TermFreq termFreq;
            if (termPos < docFreq.termCount && docFreq.terms[termPos].term.equals(term)) {
                termFreq = docFreq.terms[termPos];
            } else {
                if (docFreq.termCount >= 100) throw new RuntimeException("TermFreq full");
                for (int i = docFreq.termCount; i > termPos; i--) {
                    docFreq.terms[i] = docFreq.terms[i - 1];
                }
                termFreq = new TermFreq();
                termFreq.term = term;
                docFreq.terms[termPos] = termFreq;
                docFreq.termCount++;
            }

            termFreq.termFrequency++;
            docFreq.totalFrequency++;
        }
    }


    ///  DEBERIA HABERLO HECHO GENERICO, PERO NO LLEGUE CON EL TIEMPO

    private int binarySearchIndex(String soundexCode) {
        int left = 0, right = indexSize;
        while (left < right) {
            int mid = (left + right) / 2;
            if (index[mid].soundexCode.compareTo(soundexCode) < 0) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int binarySearchDoc(DocFreq[] arr, int size, int docId) {
        int left = 0, right = size;
        while (left < right) {
            int mid = (left + right) / 2;
            if (arr[mid].docId < docId) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int binarySearchTerm(TermFreq[] arr, int size, String term) {
        int left = 0, right = size;
        while (left < right) {
            int mid = (left + right) / 2;
            if (arr[mid].term.compareTo(term) < 0) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }


    public static void main(String[] args) {
        System.out.println("MAIN 1");
        main1(args);
        System.out.println("MAIN 2");
        main2(args);
    }

    public static void main1(String[] args) {

        SoundexInvertedIndex idx = new SoundexInvertedIndex();

        idx.addDocument(2, "robert rubin");
        idx.addDocument(1, "rupert robert");

        idx.dump();
    }

    public static void main2(String[] args) {

        SoundexInvertedIndex idx = new SoundexInvertedIndex();

        idx.addDocument(10, "carlos carlos carlos carl carl cesar cesar cesar");
        idx.addDocument(5, "carol carol carlos carlos carlos camilo camilo");
        idx.addDocument(8, "cesar cesar carla carla carla carlos");

        idx.dump();
    }

}