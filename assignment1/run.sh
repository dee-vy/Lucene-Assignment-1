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

echo "BM25_results_StandardEnglish"
./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../BM25_results_StandardEnglish.txt
echo "BM25_results_Classic"
./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../BM25_results_Classic.txt
echo "BM25_results_Whitespace"
./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../BM25_results_Whitespace.txt
echo "BM25_results_Simple"
./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../BM25_results_Simple.txt

echo "VSM_results_StandardEnglish"
./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../VSM_results_StandardEnglish.txt
echo "VSM_results_Classic"
./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../VSM_results_Classic.txt
echo "VSM_results_Whitespace"
./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../VSM_results_Whitespace.txt
echo "VSM_results_Simple"
./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../VSM_results_Simple.txt

