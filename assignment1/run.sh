#!/bin/bash


git clone https://github.com/dee-vy/Lucene-Assignment-1.git
cd Lucene-Assignment-1/
cd assignment1/

cd trec_eval/
make
cd ..
mvn clean install
mvn compile exec:java -Dexec.mainClass="IndexFile"
cd trec_eval

./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../BM25_results_StandardEnglish.txt

./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../BM25_results_Classic.txt

./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../BM25_results_Whitespace.txt


./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../BM25_results_Simple.txt

./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../VSM_results_StandardEnglish.txt

./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../VSM_results_Classic.txt
