import React, { useState, useEffect} from "react";

import Store from '../../components/HomePage/Store'
import SuperHeroBanner from '../../components/HomePage/SuperHeroBanner'
import './homepage.css'

export default function HomePage() {
    return (
        <div className="homepage-background">
            <div className="homepage-store">
                <Store/>
            </div>
        </div>
    );
}

