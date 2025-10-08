import React from 'react'
import parallaxBg from '../../../assets/imagens/parallax/parallax.png'
import BrandsHome from './BrandsHome'
import FeaturedProducts from './FeaturedProducts'


const Home = ()=> {
    return (
        <div className='bg-[#0d0d0d] h-[2500px]'>
            <section 
                className='pt-[250px] h-[1000px] bg-cover bg-center bg-fixed
                ' style={{backgroundImage: `url(${parallaxBg})`}}
                >
                <BrandsHome/>
            </section>
            <section>
                <FeaturedProducts/>
            </section>
        </div>
    )
}

export default Home


//#dfdfdf
//#0D0D0D