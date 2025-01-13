package com.project.urlShortner.dto;

import java.util.List;

public  class Config {
        private String body; // QR code body style
        private String eye; // Eye style
        private String eyeBall; // Eye ball style
        private List<String> erf1, erf2, erf3; // Eye rotations
        private List<String> brf1, brf2, brf3; // Eye ball rotations
        private String bodyColor; // Body color
        private String bgColor; // Background color
        private String eye1Color, eye2Color, eye3Color; // Eye colors
        private String eyeBall1Color, eyeBall2Color, eyeBall3Color; // Eye ball colors
        private String gradientColor1, gradientColor2; // Gradient colors
        private String gradientType; // Gradient type (linear, radial)
        private boolean gradientOnEyes; // Apply gradient on eyes
        private String logo; // Logo URL or file
        private String logoMode; // Logo mode (e.g., clean)

        public String getLogoBackGroundColour() {
                return logoBackGroundColour;
        }

        public void setLogoBackGroundColour(String logoBackGroundColour) {
                this.logoBackGroundColour = logoBackGroundColour;
        }

        public String getLogoBackGroundShape() {
                return logoBackGroundShape;
        }

        public void setLogoBackGroundShape(String logoBackGroundShape) {
                this.logoBackGroundShape = logoBackGroundShape;
        }

        private String logoBackGroundColour;

        private String logoBackGroundShape;

        // Getters and Setters

        public String getBody() {
                return body;
        }

        public void setBody(String body) {
                this.body = body;
        }

        public String getEye() {
                return eye;
        }

        public void setEye(String eye) {
                this.eye = eye;
        }

        public String getEyeBall() {
                return eyeBall;
        }

        public void setEyeBall(String eyeBall) {
                this.eyeBall = eyeBall;
        }

        public List<String> getErf1() {
                return erf1;
        }

        public void setErf1(List<String> erf1) {
                this.erf1 = erf1;
        }

        public List<String> getErf2() {
                return erf2;
        }

        public void setErf2(List<String> erf2) {
                this.erf2 = erf2;
        }

        public List<String> getErf3() {
                return erf3;
        }

        public void setErf3(List<String> erf3) {
                this.erf3 = erf3;
        }

        public List<String> getBrf1() {
                return brf1;
        }

        public void setBrf1(List<String> brf1) {
                this.brf1 = brf1;
        }

        public List<String> getBrf2() {
                return brf2;
        }

        public void setBrf2(List<String> brf2) {
                this.brf2 = brf2;
        }

        public List<String> getBrf3() {
                return brf3;
        }

        public void setBrf3(List<String> brf3) {
                this.brf3 = brf3;
        }

        public String getBodyColor() {
                return bodyColor;
        }

        public void setBodyColor(String bodyColor) {
                this.bodyColor = bodyColor;
        }

        public String getBgColor() {
                return bgColor;
        }

        public void setBgColor(String bgColor) {
                this.bgColor = bgColor;
        }

        public String getEye1Color() {
                return eye1Color;
        }

        public void setEye1Color(String eye1Color) {
                this.eye1Color = eye1Color;
        }

        public String getEye2Color() {
                return eye2Color;
        }

        public void setEye2Color(String eye2Color) {
                this.eye2Color = eye2Color;
        }

        public String getEye3Color() {
                return eye3Color;
        }

        public void setEye3Color(String eye3Color) {
                this.eye3Color = eye3Color;
        }

        public String getEyeBall1Color() {
                return eyeBall1Color;
        }

        public void setEyeBall1Color(String eyeBall1Color) {
                this.eyeBall1Color = eyeBall1Color;
        }

        public String getEyeBall2Color() {
                return eyeBall2Color;
        }

        public void setEyeBall2Color(String eyeBall2Color) {
                this.eyeBall2Color = eyeBall2Color;
        }

        public String getEyeBall3Color() {
                return eyeBall3Color;
        }

        public void setEyeBall3Color(String eyeBall3Color) {
                this.eyeBall3Color = eyeBall3Color;
        }

        public String getGradientColor1() {
                return gradientColor1;
        }

        public void setGradientColor1(String gradientColor1) {
                this.gradientColor1 = gradientColor1;
        }

        public String getGradientColor2() {
                return gradientColor2;
        }

        public void setGradientColor2(String gradientColor2) {
                this.gradientColor2 = gradientColor2;
        }

        public String getGradientType() {
                return gradientType;
        }

        public void setGradientType(String gradientType) {
                this.gradientType = gradientType;
        }

        public boolean isGradientOnEyes() {
                return gradientOnEyes;
        }

        public void setGradientOnEyes(boolean gradientOnEyes) {
                this.gradientOnEyes = gradientOnEyes;
        }

        public String getLogo() {
                return logo;
        }

        public void setLogo(String logo) {
                this.logo = logo;
        }

        public String getLogoMode() {
                return logoMode;
        }

        public void setLogoMode(String logoMode) {
                this.logoMode = logoMode;
        }
}