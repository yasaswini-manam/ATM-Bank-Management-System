**Lung Cancer Prediction using Machine Learning**

**Overview**

This project implements a Lung Cancer Prediction system using various machine learning algorithms with a hybrid feature selection technique. The goal is to assist early detection of lung cancer based on patient data, improving diagnosis accuracy and aiding medical professionals in decision-making.

The hybrid feature selection combines multiple methods to select the most relevant features, improving the predictive performance of the models.

This project was presented at the International Conference on Artificial Intelligence and Quantum Computing (ICAIQC) and will be published in IEEE.

**Features**

Data preprocessing and cleaning

Hybrid feature selection for dimensionality reduction

Implementation of multiple machine learning algorithms (e.g., Random Forest, SVM, KNN)

Model evaluation with metrics like Accuracy, Precision, Recall, F1-score

Visualization of results

**Dataset**

The dataset used contains patient information with features relevant to lung cancer diagnosis.

**Usage**

*Load and preprocess the dataset:*

from preprocessing import preprocess_data
data = preprocess_data('dataset.csv')

*Apply feature selection and train models:*

from model import train_models
models = train_models(data)

*Evaluate models and visualize results:*

from evaluation import evaluate_models
evaluate_models(models, data)

**Results**

Model accuracy and performance metrics can be displayed in tables or charts.

The hybrid feature selection improves accuracy by removing irrelevant or redundant features.

Example metrics:

Random Forest: 95% Accuracy

SVM: 92% Accuracy
