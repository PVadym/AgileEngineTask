HTML element finder

The program parses the original document to collect all the required information about the target element. Then the program find this element in diff-case HTML document that differs a bit from the original page.
You may set id target element as the third parameter.

How to BUILD:
mvn clean package

How to RUN:
java -jar AgileEngineTask-jar-with-dependencies.jar <input_origin_file_path> <input_other_sample_file_path> <target_element_id>

The third parameter is not required.

