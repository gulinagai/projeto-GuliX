import React from 'react'
import BrandsHome from './BrandsHome'
import FeaturedProducts from './FeaturedProducts'

const Home = ()=> {
    return (
        <div className='bg-[#fafafa] h-[2500px]'>
            <section className='pt-[250px] h-1/3 bg-[#fafafa]'>
                <BrandsHome/>
            </section>
            <section>
                <FeaturedProducts/>
            </section>
        </div>
    )
}

export default Home